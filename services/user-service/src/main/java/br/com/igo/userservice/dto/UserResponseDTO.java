package br.com.igo.userservice.dto;

import java.time.LocalDateTime;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        String cpf,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}