package br.com.igo.accountservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponseDTO(
        Long id,
        Long userId,
        String accountNumber,
        BigDecimal balance,
        br.com.igo.accountservice.enums.AccountStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
