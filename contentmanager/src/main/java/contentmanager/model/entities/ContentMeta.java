package contentmanager.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ContentMeta {

    @Id
    @GeneratedValue
    private Long id;
    private String hash;
    private String contentType;
    private Long size;
    private boolean temporary;

    public ContentMeta() {
    }

    public ContentMeta(String hash, String contentType, Long size, boolean temporary) {
        this.hash = hash;
        this.contentType = contentType;
        this.size = size;
        this.temporary = temporary;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public boolean getTemporary() {
        return temporary;
    }

    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
