package dev.fabiokusaba.essentials.database.repository;

import dev.fabiokusaba.essentials.database.model.AvaliacaoFisicaEntity;
import dev.fabiokusaba.essentials.dto.AvaliacaoFisicaProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;

import java.util.List;

public interface IAvaliacaoFisicaRepository extends JpaRepository<AvaliacaoFisicaEntity, Long> {

    @NativeQuery(value = """
        SELECT a.id idAluno,
               a.nome nomeAluno,
               af.id idAvaliacao,
               af.peso peso,
               af.altura altura,
               af.porcentagem_gordura_corporal porcentagemGorduraCorporal
        FROM avaliacoes_fisicas af
        INNER JOIN alunos a
        ON a.avaliacao_fisica_id = af.id
    """)
    List<AvaliacaoFisicaProjection> getAllAvaliacoes();

    @NativeQuery(value = """
        SELECT a.id idAluno,
               a.nome nomeAluno,
               af.id idAvaliacao,
               af.peso peso,
               af.altura altura,
               af.porcentagem_gordura_corporal porcentagemGorduraCorporal
        FROM avaliacoes_fisicas af
        INNER JOIN alunos a
        ON a.avaliacao_fisica_id = af.id
    """,
    countQuery = """
            SELECT COUNT(af.id)
            FROM avaliacoes_fisicas af
            INNER JOIN alunos a
            ON a.avaliacao_fisica_id = af.id
    """)
    Page<AvaliacaoFisicaProjection> getAllAvaliacoesPage(Pageable pageable);
}
