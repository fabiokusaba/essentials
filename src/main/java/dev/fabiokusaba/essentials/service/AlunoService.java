package dev.fabiokusaba.essentials.service;

import dev.fabiokusaba.essentials.database.model.AlunoEntity;
import dev.fabiokusaba.essentials.database.repository.IAlunoRepository;
import dev.fabiokusaba.essentials.dto.AlunoRequestDto;
import dev.fabiokusaba.essentials.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final IAlunoRepository alunoRepository;

    public void save(AlunoRequestDto alunoRequestDto) {
        // Apesar da validação do email na entidade é recomendável ter essa validação em código também
        var aluno = alunoRepository.findByEmail(alunoRequestDto.email());
        if (aluno.isPresent()) {
            throw new BadRequestException("Aluno já cadastrado com esse email");
        }

        // Caso não exista um aluno cadastrado com esse email podemos seguir na criação desse aluno
        alunoRepository.save(AlunoEntity.builder()
                .nome(alunoRequestDto.nome())
                .email(alunoRequestDto.email())
                .build());
    }
}
