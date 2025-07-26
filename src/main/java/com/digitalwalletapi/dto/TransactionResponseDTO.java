package com.digitalwalletapi.dto;

import com.digitalwalletapi.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(
        Long id,
        TransactionType type,
        BigDecimal amount,
        LocalDateTime timestamp,
        Long targetAccountId
) {
}
