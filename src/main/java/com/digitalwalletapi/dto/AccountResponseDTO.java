package com.digitalwalletapi.dto;


import com.digitalwalletapi.model.Account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponseDTO(
         Long id,
         BigDecimal balance,
         String accountNumber,
         LocalDateTime createdAt
) {
    public AccountResponseDTO(Account account) {
        this(account.getId(),
                account.getBalance(),
                account.getAccountNumber(),
                account.getCreatedAt());
    }
}