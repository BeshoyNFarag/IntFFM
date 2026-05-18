package com.beshoy.MIntFFM.forum;

import com.beshoy.MIntFFM.user.UserEntity;
import com.beshoy.MIntFFM.user.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    private final PostRepo postRepository;
    private final UserRepo userRepository;

    public CommentMapper(PostRepo postRepository, UserRepo userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public CommentResponseDTO toResponseDTO(CommentEntity comment) {
        if (comment == null) {
            return null;
        }

        String firstName = comment.getUser() != null ? comment.getUser().getFirstName() : null;

        return new CommentResponseDTO(
                comment.getCommentId(),
                comment.getComment(),
                comment.getPost() != null ? comment.getPost().getPostId() : null,
                firstName,
                comment.getCreatedAt(),
                null
        );
    }

    public CommentEntity toEntity(CommentCreateDTORequest dto) {
        if (dto == null) {
            return null;
        }

        PostEntity post = postRepository.findById(dto.postId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        UserEntity user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        CommentEntity comment = new CommentEntity();
        comment.setComment(dto.comment());
        comment.setPost(post);
        comment.setUser(user);

        return comment;
    }

    public List<CommentResponseDTO> toResponseDTOList(List<CommentEntity> comments) {
        if (comments == null) {
            return null;
        }

        return comments.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }


    public CommentResponseDTO toDeleteResponseDTO(CommentEntity comment, LocalDateTime deletedAt) {
        if (comment == null) {
            return null;
        }

        String firstName = comment.getUser() != null ? comment.getUser().getFirstName() : null;

        return new CommentResponseDTO(
                comment.getCommentId(),
                comment.getComment(),
                comment.getPost() != null ? comment.getPost().getPostId() : null,
                firstName,
                comment.getCreatedAt(),
                deletedAt
        );
    }
}