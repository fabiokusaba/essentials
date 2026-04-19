package dev.fabiokusaba.essentials.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "treinos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TreinoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String nome;

    // Relacionamento N-1: Muitos treinos pertencem a um aluno
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private AlunoEntity aluno;

    // Relacionamento N-N: Muitos treinos podem ter muitos exercícios
    // Usamos JoinTable para geração da tabela auxiliar que vai armazenar as relações entre treinos e exercícios
    @ManyToMany
    @JoinTable(
            name = "treinos_exercicios",
            joinColumns = @JoinColumn(name = "treino_id"),
            inverseJoinColumns = @JoinColumn(name = "exercicio_id")
    )
    private Set<ExercicioEntity> exercicios = new HashSet<>();
}
