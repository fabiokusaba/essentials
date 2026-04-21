package dev.fabiokusaba.essentials.service;

import dev.fabiokusaba.essentials.database.model.AlunoEntity;
import dev.fabiokusaba.essentials.database.model.TreinoEntity;
import dev.fabiokusaba.essentials.database.repository.IAlunoRepository;
import dev.fabiokusaba.essentials.database.repository.IAvaliacaoFisicaRepository;
import dev.fabiokusaba.essentials.database.repository.ITreinoRepository;
import dev.fabiokusaba.essentials.dto.AlunoRequestDto;
import dev.fabiokusaba.essentials.dto.AvaliacaoFisicaResponseDto;
import dev.fabiokusaba.essentials.exception.BadRequestException;
import dev.fabiokusaba.essentials.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final IAlunoRepository alunoRepository;
    private final ITreinoRepository treinoRepository;
    private final IAvaliacaoFisicaRepository avaliacaoFisicaRepository;

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

    // Quando utilizamos a anotação Transactional garantimos que todas as execuções (deletes) estão acontecendo
    // dentro de uma transação, ou seja, se todas as execuções concluíram com sucesso ao final o Spring vai fazer
    // o commit que, de fato, vai surtir efeitos em nossa base de dados
    // Caso alguma das execuções ocorra um erro inesperado ao invés do Spring dar um commit ele vai dar um rollback
    // na execução que deu falha fazendo com que a gente não perca os dados anteriormente processados com sucesso
    // É uma anotação importante para garantirmos consistência dos dados da aplicação, ou seja, nesse caso ou removemos
    // tudo ou não removemos nada
    // Por padrão o Transactional somente dá rollback em exceções do tipo UNCHECKED, mas podemos mudar esse comportamento
    // especificando o rollbackFor, desta forma ao passarmos a classe Exception garantimos que todas exceções filhas dela
    // também seja possível o rollback
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long id) {
        // Validar a existência do aluno
        var aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado"));

        // 1. Remover os treinos do aluno
        // 1.1 Pegar todos os IDs dos treinos relacionados ao aluno
        // 1.2 Fazer a remoção em lote desses IDs
        var alunoTreinosId = aluno.getTreinos().stream()
                .map(TreinoEntity::getId)
                .toList();

        treinoRepository.deleteAllById(alunoTreinosId);

        // 2. Remover o aluno em si
        alunoRepository.deleteById(id);

        // 3. Remover a avaliação física do aluno
        avaliacaoFisicaRepository.deleteById(aluno.getAvaliacaoFisicaEntity().getId());
    }
}
