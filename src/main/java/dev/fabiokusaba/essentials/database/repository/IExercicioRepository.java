package dev.fabiokusaba.essentials.database.repository;

import dev.fabiokusaba.essentials.database.model.ExercicioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IExercicioRepository extends JpaRepository<ExercicioEntity, Long> {

    // Query Method
    List<ExercicioEntity> findAllByGrupoMuscular(String grupoMuscular);

    @Query(value = "SELECT e FROM ExercicioEntity e WHERE UPPER(e.grupoMuscular) = UPPER(:grupoMuscular)")
    List<ExercicioEntity> findAllByGrupoMuscularJpql(@Param("grupoMuscular") String grupoMuscular);

    @NativeQuery(value = """
        SELECT e
        FROM exercicios e
        WHERE UPPER(e.grupo_muscular) = UPPER(:grupoMuscular)
    """)
    List<ExercicioEntity> findAllByGrupoMuscularNative(@Param("grupoMuscular") String grupoMuscular);
}
