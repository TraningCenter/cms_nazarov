package contentmanager.model.service;

import contentmanager.model.dto.ContentResponse;
import contentmanager.model.dto.ContentEditResponse;
import contentmanager.model.entities.ContentData;
import contentmanager.model.entities.ContentMeta;
import contentmanager.model.service.identity.ContentIdentityResolver;
import contentmanager.model.service.identity.Hasher;
import contentmanager.model.service.identity.IdentityResolveJob;
import contentmanager.model.service.identity.TwoFileReposHashContentIdentityResolver;
import contentmanager.model.service.repository.ContentDataRepository;
import contentmanager.model.service.repository.ContentMetaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Component
public class TwoFileReposContentManager implements ContentManager {

    private static final Logger log = LoggerFactory.getLogger(TwoFileReposHashContentIdentityResolver.class);

    @Resource(name = "persistRepository")
    ContentDataRepository persistContentDataRepository;

    @Resource(name = "tempRepository")
    ContentDataRepository tempContentDataRepository;

    @Autowired
    Hasher hasher;

    @Autowired
    ContentMetaRepository contentMetaRepository;

    @Autowired
    ContentIdentityResolver contentIdentityResolver;

    public ContentEditResponse save(MultipartFile file) {
        try {
            String hash = hasher.hash(file.getBytes());
            Boolean tempSaveSuccess = tempContentDataRepository.save(
                    new ContentData(file.getBytes(), hash)).getSuccess();

            if (!tempSaveSuccess)
                return new ContentEditResponse(false);

            ContentMeta savedContentMeta = contentMetaRepository.save(
                    new ContentMeta(hash, file.getContentType(), file.getSize(), true));

            if (savedContentMeta.getId() == null)
                return new ContentEditResponse(false);

            log.info("Saved content with {} into temp store", hash);

            contentIdentityResolver.addToJobQueue(new IdentityResolveJob(savedContentMeta.getId()));

            return new ContentEditResponse(true, hash);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ContentEditResponse(false);
    }

    @Override
    @Transactional
    public ContentEditResponse delete(String name) {

        Integer deletedDatasCount = contentMetaRepository.findAllByHash(name)
                .stream()
                .map(meta -> meta.getTemporary() ?
                        tempContentDataRepository.remove(name) :
                        persistContentDataRepository.remove(name))
                .mapToInt(contentData -> contentData.getSuccess() ? 1 : 0)
                .sum();

        Integer deletedMetasCount = contentMetaRepository.deleteAllByHash(name);

        return new ContentEditResponse(deletedDatasCount.equals(deletedMetasCount), name);
    }

    @Override
    public ContentResponse getByName(String name) {

        ContentMeta contentMetaToUse = contentMetaRepository.findByHashAndTemporaryIsTrue(name)
                .or(contentMetaRepository.findByHashAndTemporaryIsFalse(name)).orNull();

        if (contentMetaToUse == null)
            return new ContentResponse(false);

        ContentData contentData = contentMetaToUse.getTemporary() ?
                tempContentDataRepository.read(contentMetaToUse.getHash()) :
                persistContentDataRepository.read(contentMetaToUse.getHash());

        if (contentData==null)
            return new ContentResponse(false);

        return new ContentResponse(true, contentData.getBytes(),
                contentMetaToUse.getContentType(),
                contentMetaToUse.getSize(),
                contentMetaToUse.getHash());

    }
}
