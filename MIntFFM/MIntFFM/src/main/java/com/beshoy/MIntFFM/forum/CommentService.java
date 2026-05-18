package com.beshoy.MIntFFM.forum;

import com.beshoy.MIntFFM.user.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepo commentRepository;
    private final CommentMapper commentMapper;
    private final PostRepo postRepository;
    private final UserRepo userRepository;

    public CommentService(CommentRepo commentRepository, CommentMapper commentMapper,
                          PostRepo postRepository, UserRepo userRepository) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public CommentResponseDTO createComment(CommentCreateDTORequest dto) {
        validatePostExists(dto.postId());
        validateUserExists(dto.userId());

        CommentEntity comment = commentMapper.toEntity(dto);
        CommentEntity saved = commentRepository.save(comment);
        return commentMapper.toResponseDTO(saved);
    }

    public List<CommentResponseDTO> getCommentsByPost(Long postId) {
        validatePostExists(postId);
        List<CommentEntity> comments = commentRepository.findByPost_PostId(postId);
        return commentMapper.toResponseDTOList(comments);
    }



    private void validatePostExists(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new EntityNotFoundException("Post with ID " + postId + " not found");
        }
    }

    private void validateUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User with ID " + userId + " not found");
        }
    }

    private void validateUserOwnsComment(CommentEntity comment, Long userId) {
        if (!comment.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("User does not own this comment");
        }
    }

    public CommentResponseDTO deleteComment(CommentDeleteRequestDTO dto) {
        CommentEntity comment = commentRepository.findById(dto.commentId())
                .orElseThrow(() -> new EntityNotFoundException("Comment not found"));

        validateUserOwnsComment(comment, dto.userId());

        LocalDateTime deletedAt = LocalDateTime.now();
        commentRepository.deleteById(dto.commentId());

        return commentMapper.toDeleteResponseDTO(comment, deletedAt);
    }

    public List<CommentResponseDTO> getCommentsByUser(Long userId) {
        validateUserExists(userId);
        List<CommentEntity> comments = commentRepository.findByUser_UserId(userId);
        return commentMapper.toResponseDTOList(comments);
    }
}