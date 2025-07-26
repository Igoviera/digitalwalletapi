package com.digitalwalletapi.dto;

import java.math.BigDecimal;

public record TransactionRequestDTO(
        BigDecimal amount
) {
}
