package com.beshoy.MIntFFM.user;


import org.springframework.stereotype.Component;

@Component
public class UserMapper {


    public UserEntity toEntity(UserSignUpDTORequest dto) {

        if (dto == null) {
            throw new IllegalArgumentException("User Sign up information is empty it cannot be null");
        }
        UserEntity user = new UserEntity();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPassword(dto.password()); // Will be encoded in controller
        user.setBirthdate(dto.birthdate());
        return user;
    }


    public UserResponseDTO toResponseDTO(UserEntity user) {

        if (user == null) {
            throw new IllegalArgumentException("UserEntity cannot be null");
        }
        return new UserResponseDTO(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getBirthdate(),
                user.getCreatedAt()
        );
    }




}