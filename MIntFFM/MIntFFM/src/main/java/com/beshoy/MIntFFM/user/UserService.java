package com.beshoy.MIntFFM.user;


import org.springframework.stereotype.Service;



@Service
public class UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;


    public UserService(UserRepo userRepo, UserMapper userMapper) {
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }


    public UserResponseDTO signUp(UserSignUpDTORequest signUpRequest) {
        UserEntity user = userMapper.toEntity(signUpRequest);
        user.setPassword(signUpRequest.password());
        UserEntity savedUser = userRepo.save(user);
        return userMapper.toResponseDTO(savedUser);
    }


    public UserResponseDTO signIn(UserSignInDTORequest signInRequest) {
        UserEntity user = userRepo.findByEmail(signInRequest.email())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));


        if (!signInRequest.password().equals(user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return userMapper.toResponseDTO(user);
    }
}