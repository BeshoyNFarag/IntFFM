package com.beshoy.MIntFFM.user;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserSignUpDTORequest(

        @NotBlank(message = "First name is required")
        @Size(min = 3, max = 50, message = "First name must be between 2 and 50 characters")
        @Pattern(regexp = "^[A-Za-z\\s\\-']+(?<!\\s)$",
                message = "Name can only contain letters, spaces, hyphens," +
                        "and apostrophes, and cannot start or end with spaces")
        String firstName,

        @Size(min= 3, max = 50, message = "Last name cannot exceed 50 characters")
        @Pattern(regexp = "^[A-Za-z\\s\\-']+(?<!\\s)$",
                message = "Name can only contain letters, spaces, hyphens," +
                        "and apostrophes, and cannot start or end with spaces")
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")
        @Pattern(regexp = ".*[a-z].*", message = "Password must contain at least one lowercase letter")
        @Pattern(regexp = ".*[0-9].*", message = "Password must contain at least one digit")
        String password,

        @NotNull(message = "Birthdate is required")
        @Past(message = "Birthdate must be in the past")
        LocalDate birthdate
) {}