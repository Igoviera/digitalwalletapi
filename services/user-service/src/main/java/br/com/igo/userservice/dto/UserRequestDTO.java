package br.com.igo.userservice.dto;

public record UserRequestDTO(
        String name,
        String email,
        String password,
        String cpf
) {
}
