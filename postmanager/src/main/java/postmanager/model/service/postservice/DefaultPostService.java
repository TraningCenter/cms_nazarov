package postmanager.model.service.postservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;
import postmanager.model.dto.PostResponse;
import postmanager.model.dto.PostSaveRequest;
import postmanager.model.entities.Content;
import postmanager.model.entities.Post;
import postmanager.model.entities.User;
import postmanager.model.mapper.DtoMapper;
import postmanager.model.service.repository.ContentRepository;
import postmanager.model.service.repository.PostRepository;
import postmanager.model.service.repository.UserRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultPostService implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ContentRepository contentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DtoMapper dtoMapper;

    public PostResponse savePost(PostSaveRequest postSaveRequest) {
        Post postToSave = dtoMapper.map(postSaveRequest);

        postToSave.setUser(userRepository.findOne(postToSave.getUser().getId()));

        Post savedPost = postRepository.save(postToSave);

        savedPost.setContents(savedPost.getContents().
                stream()
                .map(this::findById)
                .collect(Collectors.toList()));
        savedPost.getContents().forEach(content -> content.setPost(savedPost));
        savedPost.setContents(savedPost.getContents().stream().map(contentRepository::save).collect(Collectors.toList()));

        PostResponse postResponse = dtoMapper.map(savedPost);
        postResponse.setSuccess(true);

        return postResponse;
    }

    @Override
    public PostResponse getPost(Long postId) {
        Post postById = postRepository.findOne(postId);

        if (postById == null)
            return new PostResponse(false);

        PostResponse postResponse = dtoMapper.map(postById);
        postResponse.setSuccess(postResponse.getPostId() != null);

        return postResponse;
    }

    @Override
    public PostResponse deletePost(Long postId) {
        Post postToDelete = postRepository.findOne(postId);
        if (postToDelete == null)
            return new PostResponse(true);

        postRepository.delete(postId);

        PostResponse postResponse = dtoMapper.map(postToDelete);

        postResponse.setSuccess(!postRepository.exists(postResponse.getPostId()));

        return postResponse;
    }

    private Content findById(Content content) {
        if (content.getHash() == null)
            return null;

        if (content.getId() != null && contentRepository.exists(content.getId()))
            return contentRepository.findOne(content.getId());
        else if (contentRepository.findByHash(content.getHash()) != null)
            return contentRepository.findByHash(content.getHash());
        else
            return content;
    }

}
