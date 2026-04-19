package dev.fabiokusaba.essentials.database.repository;

import dev.fabiokusaba.essentials.database.model.ExercicioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IExercicioRepository extends JpaRepository<ExercicioEntity, Long> {
}
