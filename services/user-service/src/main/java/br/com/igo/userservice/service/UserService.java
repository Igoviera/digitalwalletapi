package br.com.igo.userservice.service;

import br.com.igo.userservice.dto.UserRequestDTO;
import br.com.igo.userservice.dto.UserResponseDTO;
import br.com.igo.userservice.entity.User;
import br.com.igo.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public UserResponseDTO create(UserRequestDTO userDTO) {
        String encryptedPassword = passwordEncoder.encode(userDTO.password());

        User user = new User(
                userDTO.name(),
                userDTO.email(),
                encryptedPassword,
                userDTO.cpf()
        );

        user.setPassword(encryptedPassword);
        User savedUser = userRepository.save(user);

        return new UserResponseDTO(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getCpf(),
                savedUser.getCreatedAt(),
                savedUser.getUpdatedAt()
        );
    }
}
