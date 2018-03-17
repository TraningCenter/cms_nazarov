package postmanager.model.entities;

import org.springframework.data.mybatis.annotations.*;
import org.springframework.data.mybatis.domains.LongId;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Post extends LongId{

    private String title;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Content> contents = new LinkedList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", user=" + user +
                ", contents=" + contents +
                '}';
    }
}
