package com.beshoy.MIntFFM.forum;

import jakarta.validation.constraints.NotNull;

public record CommentDeleteRequestDTO(
        @NotNull(message = "Comment ID cannot be null")
        Long commentId,

        @NotNull(message = "User ID cannot be null")
        Long userId
) {
}