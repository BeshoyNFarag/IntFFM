package com.beshoy.MIntFFM.forum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostCreateDTORequest(
        @NotBlank(message = "Title cannot be blank")
        @Size(min = 1, max = 250, message = "Title must be between 1 and 250 characters")
        String title,

        @NotBlank(message = "Content cannot be blank")
        @Size(min = 1, max = 4000, message = "Content must be between 1 and 4000 characters")
        String content,

        @NotNull(message = "User ID cannot be null")
        Long userId
) {
}