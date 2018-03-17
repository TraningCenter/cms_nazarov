package postmanager.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

public class PostResponse {
    private Boolean success=false;
    private Long postId;
    private String title;
    @JsonProperty("user")
    private UserWithoutPostsResponse userResponse;
    @JsonProperty("contents")
    private List<ContentResponse> contentResponses = new LinkedList<>();

    public PostResponse() {
    }

    public PostResponse(Boolean success) {
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

    public UserWithoutPostsResponse getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(UserWithoutPostsResponse userResponse) {
        this.userResponse = userResponse;
    }

    public List<ContentResponse> getContentResponses() {
        return contentResponses;
    }

    public void setContentResponses(List<ContentResponse> contentResponses) {
        this.contentResponses = contentResponses;
    }
}
