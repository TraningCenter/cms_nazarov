package contentmanager.model.service.identity;

import contentmanager.model.entities.ContentMeta;

public class IdentityResolveJob {
    private Long contentId;

    public IdentityResolveJob(Long contentId) {
        this.contentId = contentId;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }
}
