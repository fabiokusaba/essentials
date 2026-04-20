package dev.fabiokusaba.essentials.dto;

import dev.fabiokusaba.essentials.database.model.ExercicioEntity;

public record ExercicioResponseDto(Long id, String nome, String grupoMuscular) {

    public static ExercicioResponseDto toResponseDto(ExercicioEntity entity) {
        return new ExercicioResponseDto(
                entity.getId(),
                entity.getNome(),
                entity.getGrupoMuscular()
        );
    }
}
