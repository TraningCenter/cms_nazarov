package contentmanager.model.dto;

public class ContentResponse {
    private byte[] bytes;
    private String contentType;
    private Boolean success;
    private Long contentLength;
    private String name;

    public ContentResponse(Boolean success, byte[] bytes, String contentType, Long contentLength, String name) {
        this.bytes = bytes;
        this.contentType = contentType;
        this.success = success;
        this.contentLength = contentLength;
        this.name = name;
    }

    public ContentResponse(Boolean success) {
        this.success = success;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Long getContentLength() {
        return contentLength;
    }

    public void setContentLength(Long contentLength) {
        this.contentLength = contentLength;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
