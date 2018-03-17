package postmanager.model.service.postservice;

import postmanager.model.dto.PostResponse;
import postmanager.model.dto.PostSaveRequest;

public interface PostService {
    PostResponse savePost(PostSaveRequest postSaveRequest);
    PostResponse getPost(Long postId);
    PostResponse deletePost(Long postId);
}
