package com.digitalwalletapi.dto;

import java.math.BigDecimal;

public record TransferRequestDTO(
        String targetAccountNumber,
        BigDecimal amount
) {
}
