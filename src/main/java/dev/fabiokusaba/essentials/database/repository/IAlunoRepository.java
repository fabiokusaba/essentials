package dev.fabiokusaba.essentials.database.repository;

import dev.fabiokusaba.essentials.database.model.AlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAlunoRepository extends JpaRepository<AlunoEntity, Long> {

    Optional<AlunoEntity> findByEmail(String email);
}
