package br.com.igo.transactionservice.dto;

import br.com.igo.transactionservice.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(
        Long id,
        Long accountId,
        TransactionType type,
        BigDecimal amount,
        String status,
        LocalDateTime createdAt
) {
}
