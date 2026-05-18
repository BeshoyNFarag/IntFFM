package com.beshoy.MIntFFM.forum;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record PostResponseDTO(
        @NotNull(message = "ID cannot be null")
        Long id,

        @NotBlank(message = "Title cannot be blank")
        @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
        String title,

        @NotBlank(message = "Content cannot be blank")
        @Size(min = 1, max = 10000, message = "Content must be between 1 and 10000 characters")
        String content,

        @Size(max = 100, message = "First name must not exceed 100 characters")
        String firstName,

        @NotNull(message = "Created date cannot be null")
        @PastOrPresent(message = "Created date cannot be in the future")
        LocalDateTime createdAt
) {
}