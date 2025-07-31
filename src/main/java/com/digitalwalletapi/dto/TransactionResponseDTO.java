package com.digitalwalletapi.dto;

import com.digitalwalletapi.enums.TransactionType;
import com.digitalwalletapi.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(
        Long id,
        TransactionType type,
        BigDecimal amount,
        LocalDateTime timestamp,
        Long targetAccountId,
        String targetUserName
) {
    public TransactionResponseDTO(Transaction tx) {
        this(
                tx.getId(),
                tx.getType(),
                tx.getAmount(),
                tx.getTimestamp(),
                tx.getTargetAccount() != null ? tx.getTargetAccount().getId() : null,
                tx.getTargetAccount() != null ? tx.getTargetAccount().getUser().getName() : null
        );
    }
}
