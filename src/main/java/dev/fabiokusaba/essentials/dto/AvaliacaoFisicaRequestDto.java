package dev.fabiokusaba.essentials.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AvaliacaoFisicaRequestDto(
        @NotNull(message = "AlunoId é obrigatório")
        Long alunoId,
        @NotNull(message = "Peso é obrigatório")
        BigDecimal peso,
        @NotNull(message = "Altura é obrigatória")
        BigDecimal altura,
        @NotNull(message = "Porcentagem de gordura corporal é obrigatória")
        BigDecimal porcentagemGorduraCorporal) {
}
