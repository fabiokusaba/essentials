package dev.fabiokusaba.essentials.database.repository;

import dev.fabiokusaba.essentials.database.model.AlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IAlunoRepository extends JpaRepository<AlunoEntity, Long> {

    Optional<AlunoEntity> findByEmail(String email);

    // Para evitar problemas de performance você pode deixar definido nas entidades o tipo de carregamento
    // LAZY e utilizar o JPQL para evitar ter que fazer duas queries e fazer uma única query trazendo as
    // informações
    // JOIN FETCH: Vai carregar também as informações de avaliação física
    @Query(value = "SELECT a FROM AlunoEntity a JOIN FETCH a.avaliacaoFisica WHERE a.id = :id")
    Optional<AlunoEntity> findByIdFetch(Long id);
}
