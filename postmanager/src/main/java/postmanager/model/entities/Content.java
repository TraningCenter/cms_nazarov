package postmanager.model.entities;

import org.springframework.data.mybatis.annotations.*;
import org.springframework.data.mybatis.domains.LongId;

import java.util.LinkedList;
import java.util.List;


@Entity
public class Content extends LongId {

    private String hash;
    private Boolean throughLink;

    @ManyToOne
    private Post post;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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

    @Override
    public String toString() {
        return "Content{" +
                "hash='" + hash + '\'' +
                ", throughLink=" + throughLink +
                ", id=" + id +
                '}';
    }
}
