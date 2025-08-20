package com.digitalwalletapi.dto;

public record UserRequestDTO(
        Long id,
        String name,
        String email,
        String password
) {}
