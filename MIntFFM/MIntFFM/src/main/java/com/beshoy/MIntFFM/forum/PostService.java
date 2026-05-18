package com.beshoy.MIntFFM.forum;


import com.beshoy.MIntFFM.user.UserRepo;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class PostService {
    private final PostRepo postRepository;
    private final PostMapper postMapper;
    private final UserRepo userRepository;



    public PostResponseDTO getPostById(Long id) {
        return postRepository.findById(id)
                .map(postMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
    }

    public List<PostResponseDTO> getAllPosts() {
        return postMapper.toResponseDTOList(postRepository.findAll());
    }

    public PostResponseDTO createPost(PostCreateDTORequest dto) {
        PostEntity post = postMapper.toEntity(dto);
        PostEntity saved = postRepository.save(post);
        return postMapper.toResponseDTO(saved);
    }

    public PostResponseDTO updatePost(Long id, PostResponseDTO dto) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));
        post.setTitle(dto.title());
        post.setContent(dto.content());
        PostEntity updated = postRepository.save(post);
        return postMapper.toResponseDTO(updated);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }


    public PostDeleteResponseDTO deletePost(PostDeleteRequestDTO dto) {
        PostEntity post = postRepository.findById(dto.postId())
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if (!post.getUser().getUserId().equals(dto.userId())) {
            throw new IllegalArgumentException("User does not own this post");
        }

        LocalDateTime deletedAt = LocalDateTime.now();

        postRepository.deleteById(dto.postId());

        return new PostDeleteResponseDTO(
                post.getPostId(),
                post.getTitle(),
                post.getUser() != null ? post.getUser().getFirstName() : null,
                post.getCreatedAt(),
                deletedAt
        );
    }

    public List<PostResponseDTO> getPostsByUser(Long userId) {
        validateUserExists(userId);
        List<PostEntity> posts = postRepository.findByUser_UserId(userId);
        return postMapper.toResponseDTOList(posts);
    }

    private void validateUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User with ID " + userId + " not found");
        }
    }
}


