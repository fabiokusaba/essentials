package dev.fabiokusaba.essentials.service;

import dev.fabiokusaba.essentials.database.model.AlunoEntity;
import dev.fabiokusaba.essentials.database.repository.IAlunoRepository;
import dev.fabiokusaba.essentials.dto.AlunoRequestDto;
import dev.fabiokusaba.essentials.dto.AvaliacaoFisicaResponseDto;
import dev.fabiokusaba.essentials.exception.BadRequestException;
import dev.fabiokusaba.essentials.exception.NotFoundException;
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

    public AvaliacaoFisicaResponseDto getAvaliacaoFisicaByAlunoId(Long id) {
        // Validando o ID do aluno
        var aluno = alunoRepository.findByIdFetch(id)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado"));

        // Caso o ID do aluno seja válido vamos pegar a sua avaliação física
        var avaliacaoFisica = aluno.getAvaliacaoFisicaEntity();

        // Validar a existência da avaliação física desse aluno
        if (avaliacaoFisica == null) {
            throw new NotFoundException("Avaliação física não encontrada para esse aluno");
        }

        // Caso a avaliação física seja válida retornamos ela
        return AvaliacaoFisicaResponseDto.toResponseDto(avaliacaoFisica);
    }
}
