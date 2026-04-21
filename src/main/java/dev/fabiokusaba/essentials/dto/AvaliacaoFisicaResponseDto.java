package dev.fabiokusaba.essentials.dto;

import dev.fabiokusaba.essentials.database.model.AvaliacaoFisicaEntity;

import java.math.BigDecimal;

public record AvaliacaoFisicaResponseDto(
        Long id,
        BigDecimal peso,
        BigDecimal altura,
        BigDecimal porcentagemGorduraCorporal
) {

    public static AvaliacaoFisicaResponseDto toResponseDto(AvaliacaoFisicaEntity entity) {
        return new AvaliacaoFisicaResponseDto(
                entity.getId(),
                entity.getPeso(),
                entity.getAltura(),
                entity.getPorcentagemGorduraCorporal()
        );
    }
}
