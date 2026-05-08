package com.beshoy.MIntFFM.user;


import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public UserEntity toEntity(UserSignUpDTORequest dto) {
        UserEntity user = new UserEntity();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPassword(dto.password()); // Will be encoded in controller
        user.setBirthdate(dto.birthdate());
        return user;
    }


    public UserResponseDTO toResponseDTO(UserEntity user) {
        return new UserResponseDTO(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getBirthdate(),
                user.getCreatedAt()
        );
    }

    public UserEntity toEntity(UserSignInDTORequest dto) {
        UserEntity user = new UserEntity();
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        return user;
    }
}