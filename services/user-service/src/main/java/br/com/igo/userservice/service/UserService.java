package br.com.igo.userservice.service;

import br.com.igo.userservice.dto.UpdateUserRequestDTO;
import br.com.igo.userservice.dto.UserRequestDTO;
import br.com.igo.userservice.dto.UserResponseDTO;
import br.com.igo.userservice.entity.User;
import br.com.igo.userservice.exception.BusinessException;
import br.com.igo.userservice.exception.ResourseNotFoundException;
import br.com.igo.userservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public UserResponseDTO create(UserRequestDTO userDTO) {

        if (userRepository.existsByEmail(userDTO.email())){
            throw new BusinessException("Email já cadastrado");
        }

        if (userRepository.existsByCpf(userDTO.cpf())){
            throw new BusinessException("CPF já cadastrado");
        }

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


    public List<UserResponseDTO> findAll(){
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCpf(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        )).toList();
    }

    public UserResponseDTO findById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourseNotFoundException("Usúario não encontrado")
                );
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCpf(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public UserResponseDTO update(Long id, UpdateUserRequestDTO dto){
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourseNotFoundException("Usuário não encontrado")
                );

        if (!user.getEmail().equals(dto.email()) && userRepository.existsByEmail(dto.email())) {
            throw new BusinessException("Email já cadastrado");
        }

        if (!user.getCpf().equals(dto.email()) && userRepository.existsByCpf(dto.cpf())){
            throw new BusinessException("CPF já cadastrado");
        }

        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setCpf(dto.cpf());

        if (!passwordEncoder.matches(dto.password(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(dto.password()));
        }

        User updatedUser = userRepository.save(user);

        return new UserResponseDTO(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail(),
                updatedUser.getCpf(),
                updatedUser.getCreatedAt(),
                updatedUser.getUpdatedAt()
        );
    }
}
