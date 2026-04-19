package dev.fabiokusaba.essentials.database.repository;

import dev.fabiokusaba.essentials.database.model.TreinoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITreinoRepository extends JpaRepository<TreinoEntity, Long> {
}
