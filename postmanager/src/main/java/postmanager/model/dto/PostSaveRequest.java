package postmanager.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

public class PostSaveRequest {
    private String title;
    private Long postId;
    private Long userId;

    @JsonProperty("contents")
    private List<ContentSaveRequest> contentSaveRequests = new LinkedList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<ContentSaveRequest> getContentSaveRequests() {
        return contentSaveRequests;
    }

    public void setContentSaveRequests(List<ContentSaveRequest> contentSaveRequests) {
        this.contentSaveRequests = contentSaveRequests;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
