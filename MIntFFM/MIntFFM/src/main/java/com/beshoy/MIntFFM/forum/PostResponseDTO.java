package com.beshoy.MIntFFM.forum;


import java.time.LocalDateTime;

public record PostResponseDTO(
        Long id,
        String title,
        String content,
        String firstName,
        LocalDateTime createdAt
) {
}