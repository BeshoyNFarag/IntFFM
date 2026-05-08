package com.beshoy.MIntFFM.forum;

import java.time.LocalDateTime;

public record CommentResponseDTO(
        Long id,
        String comment,
        Long postId,
        String username,
        LocalDateTime createdAt
) {
}