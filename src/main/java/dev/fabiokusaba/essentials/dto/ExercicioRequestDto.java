package dev.fabiokusaba.essentials.dto;

import jakarta.validation.constraints.NotBlank;

public record ExercicioRequestDto(
        // NotBlank garante validação de nulo e vazio
        @NotBlank(message = "O nome do exercício é obrigatório")
        String nome,
        @NotBlank(message = "O grupo muscular é obrigatório")
        String grupoMuscular) {
}
