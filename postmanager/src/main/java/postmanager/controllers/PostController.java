package postmanager.controllers;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import postmanager.model.dto.PostResponse;
import postmanager.model.dto.PostSaveRequest;
import postmanager.model.service.postservice.PostService;

@RestController
@RequestMapping(path = "/post")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> savePost(@RequestBody PostSaveRequest postSaveRequest) {
        PostResponse postResponse = postService.savePost(postSaveRequest);
        if (postResponse.getSuccess())
            return new ResponseEntity<>(postResponse, HttpStatus.OK);
        else
            return new ResponseEntity<>(postResponse, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        PostResponse postResponse = postService.getPost(postId);
        if (postResponse.getSuccess())
            return new ResponseEntity<>(postResponse, HttpStatus.OK);
        else
            return new ResponseEntity<>(postResponse, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/{postId}")
    public ResponseEntity<PostResponse> deletePost(@PathVariable Long postId) {
        PostResponse postResponse = postService.deletePost(postId);
        if (postResponse.getSuccess())
            return new ResponseEntity<>(postResponse, HttpStatus.OK);
        else
            return new ResponseEntity<>(postResponse, HttpStatus.BAD_REQUEST);
    }
}
