package dev.yubin.imageconverter.api.user.dto;

public record UserResponseDto(
        Long id,
        String email,
        String name
) {}