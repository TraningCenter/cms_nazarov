package ui.dto;

public class ContentDto {
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

    public ContentDto() {
    }

    public ContentDto(String hash, Boolean throughLink) {
        this.hash = hash;
        this.throughLink = throughLink;
    }

    public Boolean getThroughLink() {
        return throughLink;
    }

    public void setThroughLink(Boolean throughLink) {
        this.throughLink = throughLink;
    }
}
