package com.beshoy.MIntFFM.user;

import java.time.LocalDate;

public record UserResponseDTO(
        Long id,
        String firstName,
        String lastName,
        String email,
        LocalDate birthdate,
        LocalDate createdAt
) {
}