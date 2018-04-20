package ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

public class PostDto {
    private Boolean success=false;
    private Long postId;
    private String title;
    @JsonProperty("user")
    private UserDto userDto;
    @JsonProperty("contents")
    private List<ContentDto> contentRespons = new LinkedList<>();

    public PostDto() {
    }

    public PostDto(Boolean success) {
        this.success = success;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
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

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public List<ContentDto> getContentRespons() {
        return contentRespons;
    }

    public void setContentRespons(List<ContentDto> contentRespons) {
        this.contentRespons = contentRespons;
    }
}
