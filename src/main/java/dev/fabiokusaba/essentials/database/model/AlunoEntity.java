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
    // fetch: O tipo EAGER é um carregamento ansioso então toda vez que carregarmos informações da entidade Aluno ele
    // também vai carregar informações de avaliação física mesmo que a gente não queira essa informação. O tipo LAZY é
    // um carregamento preguiçoso então ele só vai carregar as informações de avaliação física se a gente, de fato, ir lá
    // e chamar a avaliação física do aluno
    // Por padrão o relacionamento OneToOne usa o tipo de carregamento EAGER, atenção que esse tipo de carregamento pode
    // causar problemas de performance em queries, sempre que possível prefira o tipo de carregamento LAZY
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "avaliacao_fisica_id")
    private AvaliacaoFisicaEntity avaliacaoFisicaEntity;

    // Relacionamento 1-N: Um aluno tem muitos treinos
    // Por padrão o relacionamento OneToMany usa o tipo de carregamento LAZY
    @OneToMany(mappedBy = "aluno", fetch = FetchType.LAZY)
    private Set<TreinoEntity> treinos = new HashSet<>();
}
