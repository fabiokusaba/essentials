package dev.fabiokusaba.essentials.database.repository;

import dev.fabiokusaba.essentials.database.model.AlunoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAlunoRepository extends JpaRepository<AlunoEntity, Long> {
}
