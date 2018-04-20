import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import postmanager.PostManagerApp;
import postmanager.model.dto.ContentSaveRequest;
import postmanager.model.dto.PostSaveRequest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = PostManagerApp.class)
public class PostControllerTests {
    @Autowired
    private MockMvc mvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Test
    public void shouldSavePostAndResponseWithSuccess() throws Exception {
        //Array
        PostSaveRequest postSaveRequest = new PostSaveRequest();
        postSaveRequest.setTitle("super-test-post");
        postSaveRequest.setUserId(1L);
        List<ContentSaveRequest> contentSaveRequests = new LinkedList<>();

        contentSaveRequests.add(new ContentSaveRequest() {{
            this.setContentId(null);
            this.setHash("super-test-hash1");
            this.setThroughLink(false);
        }});
        contentSaveRequests.add(new ContentSaveRequest() {{
            this.setContentId(null);
            this.setHash("super-test-hash2");
            this.setThroughLink(true);
        }});
        postSaveRequest.setContentSaveRequests(contentSaveRequests);

        //Act
        this.mvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.json(postSaveRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.postId", is(3)))
                .andExpect(jsonPath("$.user.username", is("test-username1")))
                .andExpect(jsonPath("$.contents[0].contentId", is(1)))
                .andExpect(jsonPath("$.contents[0].hash", is("test-hash1")))
                .andExpect(jsonPath("$.contents[1].contentId", is(2)))
                .andExpect(jsonPath("$.contents[1].hash", is("test-hash2")));

        this.mvc.perform(get("/post/3"));
    }

    @Test
    public void shouldDeletePostAndResponseWithoutSuccess() throws Exception {
        //Array
        PostSaveRequest postSaveRequest = new PostSaveRequest();
        postSaveRequest.setTitle("super-test-post");
        postSaveRequest.setUserId(1L);
        List<ContentSaveRequest> contentSaveRequests = new LinkedList<>();

        contentSaveRequests.add(new ContentSaveRequest() {{
            this.setContentId(1L);
            this.setHash("super-test-hash1");
        }});
        contentSaveRequests.add(new ContentSaveRequest() {{
            this.setContentId(2L);
            this.setHash("super-test-hash1");
        }});
        postSaveRequest.setContentSaveRequests(contentSaveRequests);

        //Act
        this.mvc.perform(post("/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.json(postSaveRequest)));

        this.mvc.perform(delete("/post/3"))
                .andExpect(jsonPath("$.success", is(true)));

        this.mvc.perform(get("/post/3"))
                .andExpect(jsonPath("$.success", is(false)));
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
