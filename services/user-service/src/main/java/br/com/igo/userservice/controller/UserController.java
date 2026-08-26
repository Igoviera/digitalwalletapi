package br.com.igo.userservice.controller;

import br.com.igo.userservice.dto.UserRequestDTO;
import br.com.igo.userservice.dto.UserResponseDTO;
import br.com.igo.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO userDTO){
        UserResponseDTO createUser = userService.create(userDTO);
        return ResponseEntity.ok(createUser);
    }
}
