package com.digitalwalletapi.dto;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        AccountResponseDTO account
) {}
