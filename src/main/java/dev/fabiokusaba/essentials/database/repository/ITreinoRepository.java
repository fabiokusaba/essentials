package dev.fabiokusaba.essentials.database.repository;

import dev.fabiokusaba.essentials.database.model.TreinoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ITreinoRepository extends JpaRepository<TreinoEntity, Long> {

    Optional<TreinoEntity> findByNomeAndAlunoId(String nome, Long alunoId);
}
