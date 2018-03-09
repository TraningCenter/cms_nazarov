package contentmanager.model.dto;

public class ContentEditResponse {
    private Boolean success;
    private String hash;

    public ContentEditResponse() {
    }

    public ContentEditResponse(Boolean success) {
        this.success = success;
    }

    public ContentEditResponse(Boolean success, String hash) {
        this.success = success;
        this.hash = hash;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
