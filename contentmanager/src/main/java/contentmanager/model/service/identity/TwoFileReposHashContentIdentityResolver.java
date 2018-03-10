package contentmanager.model.service.identity;

import com.google.common.base.Optional;
import contentmanager.model.entities.ContentData;
import contentmanager.model.entities.ContentMeta;
import contentmanager.model.service.repository.ContentDataRepository;
import contentmanager.model.service.repository.ContentMetaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayDeque;
import java.util.Queue;

@Component
public class TwoFileReposHashContentIdentityResolver implements ContentIdentityResolver {

    private static final Logger log = LoggerFactory.getLogger(TwoFileReposHashContentIdentityResolver.class);

    private Queue<IdentityResolveJob> jobs = new ArrayDeque<>();

    @Resource(name = "persistRepository")
    ContentDataRepository persistContentDataRepository;

    @Resource(name = "tempRepository")
    ContentDataRepository tempContentDataRepository;

    @Autowired
    ContentMetaRepository contentMetaRepository;

    @Override
    public void addToJobQueue(IdentityResolveJob job) {
        jobs.add(job);
        log.debug("IdentityResolveJob for contentId={} added to queue", job.getContentId());
    }

    @Scheduled(fixedDelay = 60000)
    public void resolveJobsFromQueue() {
        while (jobs.peek() != null) {
            resolveJob(jobs.poll());
        }
    }

    private void resolveJob(IdentityResolveJob job) {
        Optional<ContentMeta> contentMetaOptional = contentMetaRepository.findOneById(job.getContentId());
        if (!contentMetaOptional.isPresent())
            return;
        ContentMeta contentMetaFromJob = contentMetaOptional.get();

        Optional<ContentMeta> contentMetaOptionalFromPersist = contentMetaRepository.findFirstByHashAndTemporaryIsFalse(contentMetaFromJob.getHash());

        if (contentMetaOptionalFromPersist.isPresent())
            resolveJobIfExistContentFromPersist(job, contentMetaOptionalFromPersist);
        else
            resolveJobIfNotExistContentFromPersist(job, contentMetaFromJob);

        log.info("Resolved {}", contentMetaFromJob.getHash());
    }

    private void resolveJobIfNotExistContentFromPersist(IdentityResolveJob job, ContentMeta contentMetaFromJob) {
        log.info("No persist content with hash={}", contentMetaFromJob.getHash());

        resolveContentDataIfNotExistContentFromPerist(contentMetaFromJob);

        ContentMeta contentMetaFromTempStore = resolveContentMetaIfNotExistContentFromPerist(job);

        if (contentMetaFromTempStore == null) return;
        log.info("Moved content with hash={} from temp to persist", contentMetaFromTempStore.getHash());
    }

    private ContentMeta resolveContentMetaIfNotExistContentFromPerist(IdentityResolveJob job) {
        Optional<ContentMeta> contentMetaFromTempStoreOptional = contentMetaRepository.findOneById(job.getContentId());

        if (!contentMetaFromTempStoreOptional.isPresent()) {
            log.info("Error while resolving : no content meta for job with id={}", job.getContentId());
            return null;
        }

        ContentMeta contentMetaFromTempStore = contentMetaFromTempStoreOptional.get();
        contentMetaFromTempStore.setTemporary(false);
        contentMetaRepository.save(contentMetaFromTempStore);
        return contentMetaFromTempStore;
    }

    private void resolveContentDataIfNotExistContentFromPerist(ContentMeta contentMetaFromJob) {
        ContentData contentDataFromTempStore = tempContentDataRepository.readAndRemove(contentMetaFromJob.getHash());
        persistContentDataRepository.save(contentDataFromTempStore);
    }

    private void resolveJobIfExistContentFromPersist(IdentityResolveJob job, Optional<ContentMeta> contentMetaOptionalFromPersist) {
        ContentMeta contentMetaFromPersist = contentMetaOptionalFromPersist.get();
        log.info("Found persist content with hash={}", contentMetaFromPersist.getHash());

        tempContentDataRepository.remove(contentMetaFromPersist.getHash());
        contentMetaRepository.delete(job.getContentId());

        log.info("Deleted temp content with id={}", job.getContentId());
    }
}
