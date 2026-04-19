package dev.fabiokusaba.essentials.database.repository;

import dev.fabiokusaba.essentials.database.model.AvaliacaoFisicaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAvaliacaoFisicaRepository extends JpaRepository<AvaliacaoFisicaEntity, Long> {
}
