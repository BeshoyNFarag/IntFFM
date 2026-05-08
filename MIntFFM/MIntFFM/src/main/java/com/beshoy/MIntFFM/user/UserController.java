package com.beshoy.MIntFFM.user;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

public class UserController {

    private final UserRepo userRepo;
    private final UserMapper userMapper;  // Inject mapper

    public UserController(UserRepo userRepo, UserMapper userMapper) {
        this.userRepo = userRepo;

        this.userMapper = userMapper;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO signUp( @Valid @RequestBody UserSignUpDTORequest signUpRequest) {
        UserEntity user = userMapper.toEntity(signUpRequest);
        user.setPassword(user.getPassword());

        UserEntity savedUser = userRepo.save(user);
        return userMapper.toResponseDTO(savedUser);
    }

    @PostMapping("/signin")

    public ResponseEntity<UserResponseDTO> signIn(@Valid @RequestBody UserSignInDTORequest signInRequest) {
        UserEntity user = userRepo.findByEmail(signInRequest.email())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!signInRequest.password().equals(user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return ResponseEntity.ok(userMapper.toResponseDTO(user));
    }
}