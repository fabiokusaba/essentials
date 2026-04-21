package dev.fabiokusaba.essentials.dto;

import jakarta.validation.constraints.NotBlank;

public record AlunoRequestDto(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotBlank(message = "O email é obrigatório")
        String email) {
}
