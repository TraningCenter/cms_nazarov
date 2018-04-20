package ui.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

public class UserDto {
    private String username;

    @JsonProperty("posts")
    private List<PostDto> postRespons = new LinkedList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<PostDto> getPostRespons() {
        return postRespons;
    }

    public void setPostRespons(List<PostDto> postRespons) {
        this.postRespons = postRespons;
    }

}
