package com.digitalwalletapi.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponseDTO(
         Long id,
         BigDecimal balance,
         LocalDateTime createdAt
) {
}
