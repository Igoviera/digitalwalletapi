package com.digitalwalletapi.controller;

import com.digitalwalletapi.dto.CreateAccountRequestDTO;
import com.digitalwalletapi.dto.UserRequestDTO;
import com.digitalwalletapi.dto.UserResponseDTO;
import com.digitalwalletapi.model.User;
import com.digitalwalletapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/digitalwallet/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public UserResponseDTO create(@RequestBody UserRequestDTO user){
        return userService.create(user);
    }

    @GetMapping("/{id}")
    public UserResponseDTO getById(@PathVariable Long id){
        return userService.getById(id);
    }

    @GetMapping
    public List<UserResponseDTO> getAll(){
        return userService.getAll();
    }
}
