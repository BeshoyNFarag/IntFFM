package com.beshoy.MIntFFM.user;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;  // ONLY this!

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO signUp(@Valid @RequestBody UserSignUpDTORequest signUpRequest) {
        return userService.signUp(signUpRequest);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserResponseDTO> signIn(@Valid @RequestBody UserSignInDTORequest signInRequest) {
        UserResponseDTO user = userService.signIn(signInRequest);
        return ResponseEntity.ok(user);
    }
}