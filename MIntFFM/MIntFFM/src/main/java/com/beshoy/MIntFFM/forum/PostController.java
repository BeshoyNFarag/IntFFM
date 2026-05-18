package com.beshoy.MIntFFM.forum;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {

    PostRepo postRepo;
    PostService postService;

    public PostController(PostRepo postRepo, PostService postService) {
        this.postRepo = postRepo;
        this.postService = postService;
    }

    @PostMapping("/posts/create")
    public ResponseEntity<PostResponseDTO> createPost(@Valid @RequestBody PostCreateDTORequest postRequest) {
        PostResponseDTO createdPost = postService.createPost(postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }

    @DeleteMapping("/posts/delete")
    public ResponseEntity<PostDeleteResponseDTO> deletePost(@Valid @RequestBody PostDeleteRequestDTO request) {
        PostDeleteResponseDTO response = postService.deletePost(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponseDTO>> getPostsByUser(@PathVariable Long userId) {
        List<PostResponseDTO> posts = postService.getPostsByUser(userId);
        return ResponseEntity.ok(posts);
    }
}
