package ui.dto;

import java.util.LinkedList;

public class PostViewDto {
    private Long postId;
    private String title;
    private UserDto User;
    private String content;

    public PostViewDto(Long postId, String title, UserDto user, String content) {
        this.postId = postId;
        this.title = title;
        User = user;
        this.content = content;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserDto getUser() {
        return User;
    }

    public void setUser(UserDto user) {
        User = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
