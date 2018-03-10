package contentmanager.model.entities;

public class ContentData {

    private byte[] bytes;
    private String name;
    private Boolean success;

    public ContentData(byte[] bytes, String name) {
        this.bytes = bytes;
        this.name = name;
    }

    public ContentData(byte[] bytes, String name, Boolean success) {
        this.bytes = bytes;
        this.name = name;
        this.success = success;
    }

    public ContentData(String name, Boolean success) {
        this.name = name;
        this.success = success;
    }

    public ContentData(ContentData contentData, Boolean success) {
        this.bytes= contentData.getBytes();
        this.name = contentData.getName();
        this.success = success;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
