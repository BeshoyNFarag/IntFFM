package com.beshoy.MIntFFM.forum;

import jakarta.validation.constraints.NotNull;

public record PostDeleteRequestDTO(
        @NotNull(message = "Post ID cannot be null")
        Long postId,

        @NotNull(message = "User ID cannot be null")
        Long userId
) {
}