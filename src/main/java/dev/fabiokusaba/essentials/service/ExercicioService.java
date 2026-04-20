package dev.fabiokusaba.essentials.service;

import dev.fabiokusaba.essentials.database.model.ExercicioEntity;
import dev.fabiokusaba.essentials.database.repository.IExercicioRepository;
import dev.fabiokusaba.essentials.dto.ExercicioRequestDto;
import dev.fabiokusaba.essentials.dto.ExercicioResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExercicioService {

    private final IExercicioRepository exercicioRepository;

    public List<ExercicioResponseDto> findAll() {
        return exercicioRepository.findAll()
                .stream()
                .map(ExercicioResponseDto::toResponseDto)
                .toList();
    }

    public void save(ExercicioRequestDto exercicioRequestDto) {
        var entity = ExercicioEntity.builder()
                .nome(exercicioRequestDto.nome())
                .grupoMuscular(exercicioRequestDto.grupoMuscular())
                .build();

        exercicioRepository.save(entity);
    }

    public List<ExercicioResponseDto> getExerciciosByGrupoMuscular(String grupoMuscular) {
        return exercicioRepository.findAllByGrupoMuscular(grupoMuscular)
                .stream()
                .map(ExercicioResponseDto::toResponseDto)
                .toList();
    }
}
