package com.beshoy.MIntFFM.forum;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/create")
    public ResponseEntity<CommentResponseDTO> createComment(@Valid @RequestBody CommentCreateDTORequest request) {
        CommentResponseDTO response = commentService.createComment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //gets all the comments on a post

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByPost(@PathVariable Long postId) {
        List<CommentResponseDTO> comments = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CommentResponseDTO> deleteComment(@Valid @RequestBody CommentDeleteRequestDTO request) {
        CommentResponseDTO response = commentService.deleteComment(request);
        return ResponseEntity.ok(response);
    }

    //gets all the comments by a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByUser(@PathVariable Long userId) {
        List<CommentResponseDTO> comments = commentService.getCommentsByUser(userId);
        return ResponseEntity.ok(comments);
    }
}