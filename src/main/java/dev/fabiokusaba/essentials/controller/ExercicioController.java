package dev.fabiokusaba.essentials.controller;

import dev.fabiokusaba.essentials.dto.ExercicioRequestDto;
import dev.fabiokusaba.essentials.dto.ExercicioResponseDto;
import dev.fabiokusaba.essentials.service.ExercicioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/exercicios")
@RequiredArgsConstructor
@Validated
public class ExercicioController {

    private final ExercicioService exercicioService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ExercicioResponseDto> findAll() {
        return exercicioService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody ExercicioRequestDto exercicioRequestDto) {
        exercicioService.save(exercicioRequestDto);
    }

    @GetMapping("/grupo/{grupoMuscular}")
    @ResponseStatus(HttpStatus.OK)
    public List<ExercicioResponseDto> getExerciciosByGrupoMuscular(@PathVariable String grupoMuscular) {
        return exercicioService.getExerciciosByGrupoMuscular(grupoMuscular);
    }
}
