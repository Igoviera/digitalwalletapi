package com.digitalwalletapi.service;

import com.digitalwalletapi.dto.UserRequestDTO;
import com.digitalwalletapi.dto.UserResponseDTO;
import com.digitalwalletapi.model.User;

import java.util.List;

public interface UserService {
    UserResponseDTO create(UserRequestDTO user);

    UserResponseDTO getById(Long userId);

    List<UserResponseDTO> getAll();
}
