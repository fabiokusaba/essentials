package dev.fabiokusaba.essentials.dto;

public record TokenResponseDto(
        String token,
        Long expiresIn
) {
}
