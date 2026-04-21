package dev.fabiokusaba.essentials.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record TreinoRequestDto(
        @NotEmpty(message = "ID do aluno é obrigatório")
        Long alunoId,
        @NotBlank(message = "Nome do treino é obrigatório")
        String nome,
        @NotEmpty(message = "Lista de exercícios é obrigatória")
        List<Long> exerciciosId
) {
}
