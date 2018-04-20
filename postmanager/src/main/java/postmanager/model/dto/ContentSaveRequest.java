package postmanager.model.dto;

public class ContentSaveRequest {
    private Long contentId;
    private String hash;
    private Boolean throughLink;

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Boolean getThroughLink() {
        return throughLink;
    }

    public void setThroughLink(Boolean throughLink) {
        this.throughLink = throughLink;
    }
}
