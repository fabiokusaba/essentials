package dev.fabiokusaba.essentials.service;

import dev.fabiokusaba.essentials.database.model.ExercicioEntity;
import dev.fabiokusaba.essentials.database.model.TreinoEntity;
import dev.fabiokusaba.essentials.database.repository.IAlunoRepository;
import dev.fabiokusaba.essentials.database.repository.IExercicioRepository;
import dev.fabiokusaba.essentials.database.repository.ITreinoRepository;
import dev.fabiokusaba.essentials.dto.TreinoRequestDto;
import dev.fabiokusaba.essentials.exception.BadRequestException;
import dev.fabiokusaba.essentials.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TreinoService {

    private final ITreinoRepository treinoRepository;
    private final IExercicioRepository exercicioRepository;
    private final IAlunoRepository alunoRepository;

    public void save(TreinoRequestDto treinoRequestDto) {
        Set<ExercicioEntity> exercicios = new HashSet<>();

        // Validar a existência do aluno
        var aluno = alunoRepository.findById(treinoRequestDto.alunoId())
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado"));

        // Validar se o aluno não está criando mais de um treino com o mesmo nome
        var treino = treinoRepository.findByNomeAndAlunoId(treinoRequestDto.nome(), treinoRequestDto.alunoId());
        if (treino.isPresent()) {
            throw new BadRequestException("Não é possível criar treinos com o mesmo nome");
        }

        // Caso não exista um treino com esse nome para o aluno podemos avançar para a sua criação
        // Mas, antes de criar o treino em si precisamos validar a lista de exercícios que foi passada
        for (Long exercicioId : treinoRequestDto.exerciciosId()) {
            var exercicio = exercicioRepository.findById(exercicioId)
                    .orElseThrow(() -> new NotFoundException(String.format("Exercício %s não encontrado", exercicioId)));

            // Exercício válido, adicionamos na nossa lista
            exercicios.add(exercicio);
        }

        // Com tudo validado podemos montar o nosso treino
        treinoRepository.save(TreinoEntity.builder()
                .nome(treinoRequestDto.nome())
                .aluno(aluno)
                .exercicios(exercicios)
                .build());
    }
}
