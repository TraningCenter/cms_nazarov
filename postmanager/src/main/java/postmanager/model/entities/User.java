package postmanager.model.entities;

import org.springframework.data.mybatis.annotations.Entity;
import org.springframework.data.mybatis.annotations.OneToMany;
import org.springframework.data.mybatis.domains.LongId;

import java.util.List;

@Entity
public class User extends LongId{

    private String username;
    private String password;

    @OneToMany
    private List<Post> posts;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
