package postmanager.model.mapper;

import org.springframework.stereotype.Component;
import postmanager.model.dto.*;
import postmanager.model.entities.Content;
import postmanager.model.entities.Post;
import postmanager.model.entities.User;

import java.util.stream.Collectors;

@Component
public class DtoMapper {

    public UserResponse map(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setPostResponses(user.getPosts()
                .stream()
                .map(post -> map(post))
                .collect(Collectors.toList())
        );

        return userResponse;
    }

    public UserWithoutPostsResponse mapWithoutPosts(User user) {
        UserWithoutPostsResponse userResponse = new UserWithoutPostsResponse();
        userResponse.setUsername(user.getUsername());

        return userResponse;
    }

    public Post map(PostSaveRequest dto) {
        Post post = new Post();
        post.setId(dto.getPostId());
        post.setTitle(dto.getTitle());
        post.setUser(new User() {{
            this.setId(dto.getUserId());
        }});
        post.setContents(dto.getContentSaveRequests().stream()
                .map(contentDto -> map(contentDto))
                .collect(Collectors.toList()));

        return post;
    }

    public Post mapWithoutUserAndContents(PostSaveRequest dto) {
        Post post = new Post();
        post.setId(dto.getPostId());
        post.setTitle(dto.getTitle());

        return post;
    }

    public PostResponse map(Post post) {
        PostResponse postResponse = new PostResponse();
        postResponse.setPostId(post.getId());
        postResponse.setTitle(post.getTitle());
        postResponse.setUserResponse(mapWithoutPosts(post.getUser()));
        postResponse.setContentResponses(post.getContents()
                .stream()
                .map(content -> map(content))
                .collect(Collectors.toList()));

        return postResponse;
    }

    public ContentResponse map(Content content) {
        ContentResponse contentResponse = new ContentResponse();
        contentResponse.setContentId(content.getId());
        contentResponse.setHash(content.getHash());
        contentResponse.setThroughLink(content.getThroughLink());

        return contentResponse;
    }

    public Content map(ContentSaveRequest request) {
        Content content = new Content();
        content.setHash(request.getHash());
        content.setId(request.getContentId());
        content.setThroughLink(request.getThroughLink());

        return content;
    }
}
