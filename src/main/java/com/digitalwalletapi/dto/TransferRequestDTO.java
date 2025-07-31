package com.digitalwalletapi.dto;

import java.math.BigDecimal;

public record TransferRequestDTO(
        Long targetAccountId,
        BigDecimal amount
) {
}
