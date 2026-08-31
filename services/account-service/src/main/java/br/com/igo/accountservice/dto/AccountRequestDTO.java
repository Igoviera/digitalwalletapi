package br.com.igo.accountservice.dto;

import jakarta.validation.constraints.NotNull;

public record AccountRequestDTO(
        @NotNull(message = "userId é obrigatório")
        Long userId
) {
}
