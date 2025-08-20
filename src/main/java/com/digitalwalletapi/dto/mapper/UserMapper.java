package com.digitalwalletapi.dto.mapper;

import com.digitalwalletapi.dto.AccountResponseDTO;
import com.digitalwalletapi.dto.UserRequestDTO;
import com.digitalwalletapi.dto.UserResponseDTO;
import com.digitalwalletapi.model.User;

public class UserMapper {

    public static UserResponseDTO toDTO(User user){
        if (user == null){
            return null;
        }

       return new UserResponseDTO(
               user.getId(),
               user.getName(),
               user.getEmail(),
               new AccountResponseDTO(user.getAccount())
       );
    }

    public static User toEntity(UserRequestDTO dto){
        if (dto == null){
            return null;
        }

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        return user;
    }
}
