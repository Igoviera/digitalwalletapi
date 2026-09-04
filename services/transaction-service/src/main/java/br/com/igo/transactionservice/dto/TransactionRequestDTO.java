package br.com.igo.transactionservice.dto;

import br.com.igo.transactionservice.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransactionRequestDTO(
        @NotNull
        Long accountId,

        @NotNull
        TransactionType type,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal amount
) {
}
