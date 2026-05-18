package com.beshoy.MIntFFM.forum;


import com.beshoy.MIntFFM.user.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.beshoy.MIntFFM.user.UserEntity;

@Component
public class PostMapper {

    private final UserRepo userRepository;


    public PostMapper(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    public PostResponseDTO toResponseDTO(PostEntity post) {
        if (post == null) {
            return null;
        }

        UserEntity user = post.getUser();

        String firstName =  user.getFirstName();

        return new PostResponseDTO(
                post.getPostId(),
                post.getTitle(),
                post.getContent(),
                firstName,
                post.getCreatedAt()
        );
    }

    public PostEntity toEntity(PostCreateDTORequest dto) {
        if (dto == null) {
            return null;
        }

        PostEntity post = new PostEntity();
        post.setTitle(dto.title());
        post.setContent(dto.content());

        UserEntity user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        post.setUser(user);

        return post;
    }

    public List<PostResponseDTO> toResponseDTOList(List<PostEntity> posts) {
        if (posts == null) {
            return null;
        }

        return posts.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public PostDeleteResponseDTO toDeleteResponseDTO(PostEntity post, LocalDateTime deletedAt) {
        if (post == null) {
            return null;
        }

        return new PostDeleteResponseDTO(
                post.getPostId(),
                post.getTitle(),
                post.getUser() != null ? post.getUser().getFirstName() : null,
                post.getCreatedAt(),
                deletedAt
        );
    }
}