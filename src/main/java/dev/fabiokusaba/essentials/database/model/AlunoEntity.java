package dev.fabiokusaba.essentials.database.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "alunos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlunoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String email;

    // Relacionamento 1-1: Um aluno tem uma avaliação física
    // cascade: Faz as alterações em cascata, então o que for alterado na entidade de alunos vai propagar as alterações
    // para a entidade de avaliações físicas
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "avaliacao_fisica_id")
    private AvaliacaoFisicaEntity avaliacaoFisicaEntity;

    // Relacionamento 1-N: Um aluno tem muitos treinos
    @OneToMany(mappedBy = "aluno")
    private Set<TreinoEntity> treinos = new HashSet<>();
}
