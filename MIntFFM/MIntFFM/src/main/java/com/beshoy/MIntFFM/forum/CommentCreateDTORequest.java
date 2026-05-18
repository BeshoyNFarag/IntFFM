package com.beshoy.MIntFFM.forum;


import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentCreateDTORequest(
        @NotBlank(message = "Comment cannot be empty")
        @Size(min = 1, max = 4000)
        String comment,

        @NotNull(message = "Post ID cannot be null")
        Long postId,

        @NotNull(message = "User ID cannot be null")
        Long userId
) {
}