package ui.service;

import ui.dto.PostDto;
import ui.dto.PostSaveRequest;

public interface PostManager {
    PostDto savePost(PostSaveRequest postSaveRequest);
    PostDto getPost(Integer id);
}
