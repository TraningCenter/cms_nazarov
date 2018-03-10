import com.google.common.base.Optional;
import contentmanager.ContentManagerApp;
import contentmanager.model.entities.ContentData;
import contentmanager.model.entities.ContentMeta;
import contentmanager.model.service.identity.ContentIdentityResolver;
import contentmanager.model.service.identity.TwoFileReposHashContentIdentityResolver;
import contentmanager.model.service.repository.ContentDataRepository;
import contentmanager.model.service.repository.ContentMetaRepository;
import contentmanager.model.service.identity.Hasher;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.annotation.Resource;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = ContentManagerApp.class)
@TestPropertySource(properties = "app.scheduling.enable=false")
public class ContentControllerTests {
    @Autowired
    private MockMvc mvc;

    @Resource(name = "tempRepository")
    ContentDataRepository tempContentDataRepository;

    @Resource(name = "persistRepository")
    ContentDataRepository persistContentDataRepository;

    @Autowired
    ContentMetaRepository contentMetaRepository;

    @Autowired
    Hasher hasher;

    @Autowired
    TwoFileReposHashContentIdentityResolver contentIdentityResolver;

    @Test
    public void shouldSaveFileToTempRepositoryAndAddMeta() throws Exception {
        //Array
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", "Spring Framework".getBytes());

        String hashExpected = hasher.hash(multipartFile.getBytes());
        byte[] bytesExpected = multipartFile.getBytes();

        //Act
        MvcResult mvcResult = this.mvc.perform(fileUpload("/content").file(multipartFile))
                .andExpect(status().isOk())
                .andReturn();

        Optional<ContentMeta> contentMetaOptional = contentMetaRepository.findByHash(hashExpected);

        //Assert
        Assert.assertTrue(
                mvcResult.getResponse().getContentAsString()
                        .equals("{\"success\":true,\"hash\":\"" + hashExpected + "\"}"));
        Assert.assertTrue(Arrays.equals(tempContentDataRepository.read(hashExpected).getBytes(), bytesExpected));

        Assert.assertTrue(contentMetaOptional.isPresent());
        Assert.assertTrue(contentMetaOptional.get().getTemporary());
        Assert.assertEquals(hashExpected, contentMetaOptional.get().getHash());
        Assert.assertEquals("text/plain", contentMetaOptional.get().getContentType());
        Assert.assertEquals(multipartFile.getSize(), contentMetaOptional.get().getSize().longValue());

        contentMetaRepository.delete(contentMetaOptional.get().getId());
        tempContentDataRepository.remove(hashExpected);
    }

    @Test
    public void shouldResolveSingleTempContent() throws Exception {
        //Array
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", "Spring Framework".getBytes());

        String hashExpected = hasher.hash(multipartFile.getBytes());
        byte[] bytesExpected = multipartFile.getBytes();

        //Act
        MvcResult mvcResult = this.mvc.perform(fileUpload("/content").file(multipartFile))
                .andExpect(status().isOk())
                .andReturn();

        contentIdentityResolver.resolveJobsFromQueue();

        Optional<ContentMeta> contentMetaOptional = contentMetaRepository.findByHash(hashExpected);

        Long contentsCountByHash = contentMetaRepository.countAllByHash(hashExpected);

        ContentData contentDataFromPersistStore = persistContentDataRepository.read(hashExpected);

        //Assert
        Assert.assertFalse(tempContentDataRepository.read(hashExpected).getSuccess());
        Assert.assertTrue(contentDataFromPersistStore.getSuccess());
        Assert.assertEquals(hashExpected, contentDataFromPersistStore.getName());
        Assert.assertTrue(Arrays.equals(contentDataFromPersistStore.getBytes(), bytesExpected));

        Assert.assertTrue(contentMetaOptional.isPresent());
        Assert.assertFalse(contentMetaOptional.get().getTemporary());
        Assert.assertEquals(hashExpected, contentMetaOptional.get().getHash());
        Assert.assertEquals("text/plain", contentMetaOptional.get().getContentType());

        Assert.assertEquals(1, contentsCountByHash.longValue());

        contentMetaRepository.delete(contentMetaOptional.get().getId());
        persistContentDataRepository.remove(hashExpected);
    }

    @Test
    public void shouldReturnContentFromTempStore() throws Exception {
        //Array
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", "Spring Framework".getBytes());

        String hashExpected = hasher.hash(multipartFile.getBytes());
        byte[] bytesExpected = multipartFile.getBytes();

        //Act
        MvcResult mvcResult = this.mvc.perform(fileUpload("/content").file(multipartFile))
                .andExpect(status().isOk())
                .andReturn();

        Optional<ContentMeta> contentMetaOptional = contentMetaRepository.findByHash(hashExpected);

        MvcResult mvcGetContentResult = this.mvc.perform(get("/content/"+hashExpected))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        Assert.assertTrue(Arrays.equals(bytesExpected, mvcGetContentResult.getResponse().getContentAsByteArray()));
        Assert.assertEquals("text/plain", mvcGetContentResult.getResponse().getContentType());

        contentMetaRepository.delete(contentMetaOptional.get().getId());
        tempContentDataRepository.remove(hashExpected);
    }

    @Test
    public void shouldReturnContentFromPersistStoreAfterResolve() throws Exception {
        //Array
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", "Spring Framework".getBytes());

        String hashExpected = hasher.hash(multipartFile.getBytes());
        byte[] bytesExpected = multipartFile.getBytes();

        //Act
        MvcResult mvcResult = this.mvc.perform(fileUpload("/content").file(multipartFile))
                .andExpect(status().isOk())
                .andReturn();

        contentIdentityResolver.resolveJobsFromQueue();

        Optional<ContentMeta> contentMetaOptional = contentMetaRepository.findByHash(hashExpected);

        MvcResult mvcGetContentResult = this.mvc.perform(get("/content/"+hashExpected))
                .andExpect(status().isOk())
                .andReturn();

        //Assert
        Assert.assertTrue(Arrays.equals(bytesExpected, mvcGetContentResult.getResponse().getContentAsByteArray()));
        Assert.assertEquals("text/plain", mvcGetContentResult.getResponse().getContentType());

        contentMetaRepository.delete(contentMetaOptional.get().getId());
        tempContentDataRepository.remove(hashExpected);
    }

    @Test
    public void shouldDeleteAllContentAndMetaFromStores() throws Exception {
        //Array
        MockMultipartFile multipartFile = new MockMultipartFile("file", "test.txt",
                "text/plain", "Spring Framework".getBytes());

        String hashExpected = hasher.hash(multipartFile.getBytes());
        byte[] bytesExpected = multipartFile.getBytes();

        //Act
        MvcResult mvcResultUpload1 = this.mvc.perform(fileUpload("/content").file(multipartFile))
                .andExpect(status().isOk())
                .andReturn();

        contentIdentityResolver.resolveJobsFromQueue();

        MvcResult mvcResultUpload2 = this.mvc.perform(fileUpload("/content").file(multipartFile))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult mvcResultDelete = this.mvc.perform(delete("/content/"+hashExpected))
                .andExpect(status().isOk())
                .andReturn();

        Optional<ContentMeta> metaByHashOptional = contentMetaRepository.findByHash(hashExpected);
        ContentData tempDataByHashOptional = tempContentDataRepository.read(hashExpected);
        ContentData persisitDataByHashOptional = persistContentDataRepository.read(hashExpected);

        //Assert
        Assert.assertFalse(metaByHashOptional.isPresent());
        Assert.assertFalse(tempDataByHashOptional.getSuccess());
        Assert.assertFalse(persisitDataByHashOptional.getSuccess());

    }
}
