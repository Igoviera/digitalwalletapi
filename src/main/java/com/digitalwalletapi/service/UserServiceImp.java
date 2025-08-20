package com.digitalwalletapi.service;

import com.digitalwalletapi.dto.UserRequestDTO;
import com.digitalwalletapi.dto.UserResponseDTO;
import com.digitalwalletapi.dto.mapper.UserMapper;
import com.digitalwalletapi.model.Account;
import com.digitalwalletapi.model.User;
import com.digitalwalletapi.repository.UserRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRespository userRespository;

    @Autowired
    private AccountService accountService;

    @Override
    @Transactional
    public UserResponseDTO create(UserRequestDTO user) {
        User savedUser = userRespository.save(UserMapper.toEntity(user));
        Account account = accountService.createAccount(savedUser);
        savedUser.setAccount(account);
        return UserMapper.toDTO(savedUser);
    }

    @Override
    public UserResponseDTO getById(Long userId) {
        return UserMapper.toDTO(userRespository.findById(userId).orElseThrow(() -> new RuntimeException("Não encontrado")));
    }

    @Override
    public List<UserResponseDTO> getAll() {
       List<User> users = userRespository.findAll();

       return users.stream()
               .map(user -> UserMapper.toDTO(user))
               .collect(Collectors.toList());
    }
}
