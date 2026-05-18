package com.beshoy.MIntFFM.user;

import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public UserService(UserRepo userRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }


    public UserResponseDTO signUp(UserSignUpDTORequest signUpRequest) {

        if (signUpRequest == null) {
            throw new IllegalArgumentException("SignUp request cannot be null");
        }


        validateSignUpRequest(signUpRequest);


        if (userRepo.existsByEmail(signUpRequest.email())) {
            throw new RuntimeException("Email already exists: " + signUpRequest.email());
        }


        UserEntity user = userMapper.toEntity(signUpRequest);
        UserEntity savedUser = userRepo.save(user);


        return userMapper.toResponseDTO(savedUser);
    }


    public UserResponseDTO signIn(UserSignInDTORequest signInRequest) {

        if (signInRequest == null) {
            throw new IllegalArgumentException("SignIn request cannot be null");
        }


        if (signInRequest.email() == null || signInRequest.email().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }

        if (signInRequest.password() == null || signInRequest.password().isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }


        UserEntity user = userRepo.findByEmail(signInRequest.email())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));


        if (!signInRequest.password().equals(user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }


        return userMapper.toResponseDTO(user);
    }



    private void validateSignUpRequest(UserSignUpDTORequest request) {

        if (request.firstName() == null || request.firstName().isBlank()) {
            throw new IllegalArgumentException("First name is required");
        }
        if (request.firstName().length() < 2 || request.firstName().length() > 50) {
            throw new IllegalArgumentException("First name must be between 2 and 50 characters");
        }
        if (!request.firstName().matches("^[A-Za-z\\s\\-']+(?<!\\s)$")) {
            throw new IllegalArgumentException("First name contains invalid characters");
        }


        if (request.lastName() == null || request.lastName().isBlank()) {
            throw new IllegalArgumentException("Last name is required");
        }
        if (request.lastName().length() > 50) {
            throw new IllegalArgumentException("Last name cannot exceed 50 characters");
        }
        if (!request.lastName().matches("^[A-Za-z\\s\\-']+(?<!\\s)$")) {
            throw new IllegalArgumentException("Last name contains invalid characters");
        }


        if (request.email() == null || request.email().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (!request.email().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }


        if (request.password() == null || request.password().isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (request.password().length() < 8 || request.password().length() > 100) {
            throw new IllegalArgumentException("Password must be between 8 and 100 characters");
        }
        if (!request.password().matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter");
        }
        if (!request.password().matches(".*[a-z].*")) {
            throw new IllegalArgumentException("Password must contain at least one lowercase letter");
        }
        if (!request.password().matches(".*[0-9].*")) {
            throw new IllegalArgumentException("Password must contain at least one digit");
        }


        if (request.birthdate() == null) {
            throw new IllegalArgumentException("Birthdate is required");
        }
        if (request.birthdate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birthdate must be in the past");
        }


        LocalDate thirteenYearsAgo = LocalDate.now().minusYears(13);
        if (request.birthdate().isAfter(thirteenYearsAgo)) {
            throw new IllegalArgumentException("User must be at least 13 years old");
        }
    }
}