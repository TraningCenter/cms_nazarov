package postmanager.model.dto;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

public class UserResponse {

    private String username;

    @JsonProperty("posts")
    private List<PostResponse> postResponses = new LinkedList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<PostResponse> getPostResponses() {
        return postResponses;
    }

    public void setPostResponses(List<PostResponse> postResponses) {
        this.postResponses = postResponses;
    }
}
