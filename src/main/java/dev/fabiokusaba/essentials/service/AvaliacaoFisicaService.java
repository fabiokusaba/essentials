package dev.fabiokusaba.essentials.service;

import dev.fabiokusaba.essentials.database.model.AvaliacaoFisicaEntity;
import dev.fabiokusaba.essentials.database.repository.IAlunoRepository;
import dev.fabiokusaba.essentials.database.repository.IAvaliacaoFisicaRepository;
import dev.fabiokusaba.essentials.dto.AvaliacaoFisicaProjection;
import dev.fabiokusaba.essentials.dto.AvaliacaoFisicaRequestDto;
import dev.fabiokusaba.essentials.exception.BadRequestException;
import dev.fabiokusaba.essentials.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliacaoFisicaService {

    private final IAvaliacaoFisicaRepository avaliacaoFisicaRepository;
    private final IAlunoRepository alunoRepository;


    public void save(AvaliacaoFisicaRequestDto avaliacaoFisicaRequestDto) throws Exception {
        // Quando for criar uma avaliação precisa vincular a um aluno, então preciso validar a existência do aluno
        var aluno = alunoRepository.findById(avaliacaoFisicaRequestDto.alunoId())
                .orElseThrow(() -> new NotFoundException("Aluno não encontrado"));

        // Validar se o aluno já possui uma avaliação ou não, pois a nossa regra de negócio só permite uma avaliação
        // por aluno
        var avaliacaoFisica = aluno.getAvaliacaoFisicaEntity();
        if (avaliacaoFisica != null) {
            throw new BadRequestException("Avaliação física já cadastrada para esse aluno");
        }

        // Caso o aluno não tenha uma avaliação física avançamos para a etapa de criação dessa avaliação
        avaliacaoFisica = AvaliacaoFisicaEntity.builder()
                .peso(avaliacaoFisicaRequestDto.peso())
                .altura(avaliacaoFisicaRequestDto.altura())
                .porcentagemGorduraCorporal(avaliacaoFisicaRequestDto.porcentagemGorduraCorporal())
                .build();

        // Salvando a avaliação no aluno e persistindo também na tabela de avaliações físicas porque estamos utilizando o
        // cascade dentro do relacionamento
        aluno.setAvaliacaoFisicaEntity(avaliacaoFisica);
        alunoRepository.save(aluno);
    }

    public List<AvaliacaoFisicaProjection> getAllAvaliacoes() {
        return avaliacaoFisicaRepository.getAllAvaliacoes();
    }

    public Page<AvaliacaoFisicaProjection> getAllAvaliacoesPageable(Integer page, Integer size) {
        return avaliacaoFisicaRepository.getAllAvaliacoesPage(PageRequest.of(page, size));
    }
}
