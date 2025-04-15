package dev.yubin.imageconverter.api.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDto(
        @Email @NotBlank String email,
        @NotBlank String name,
        @NotBlank String password
) {}