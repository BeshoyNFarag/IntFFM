package com.beshoy.MIntFFM.forum;

import java.time.LocalDateTime;

public record PostDeleteResponseDTO(
        Long postId,
        String title,
        String publisherName,
        LocalDateTime createdAt,
        LocalDateTime deletedAt
) {
}