package dev.fabiokusaba.essentials.database.model;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlunoEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String senha;

    // Relacionamento 1-1: Um aluno tem uma avaliação física
    // cascade: Faz as alterações em cascata, então o que for alterado na entidade de alunos vai propagar as alterações
    // para a entidade de avaliações físicas
    // fetch: O tipo EAGER é um carregamento ansioso então toda vez que carregarmos informações da entidade Aluno ele
    // também vai carregar informações de avaliação física mesmo que a gente não queira essa informação. O tipo LAZY é
    // um carregamento preguiçoso então ele só vai carregar as informações de avaliação física se a gente, de fato, ir lá
    // e chamar a avaliação física do aluno
    // Por padrão o relacionamento OneToOne usa o tipo de carregamento EAGER, atenção que esse tipo de carregamento pode
    // causar problemas de performance em queries, sempre que possível prefira o tipo de carregamento LAZY
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "avaliacao_fisica_id")
    private AvaliacaoFisicaEntity avaliacaoFisicaEntity;

    // Relacionamento 1-N: Um aluno tem muitos treinos
    // Por padrão o relacionamento OneToMany usa o tipo de carregamento LAZY
    @OneToMany(mappedBy = "aluno", fetch = FetchType.LAZY)
    private Set<TreinoEntity> treinos = new HashSet<>();

    // Relacionamento N-N: Um aluno tem muitos papéis e um papel tem muitos alunos
    // Precisamos ligar a nossa tabela de alunos com a nossa tabela de roles, cada aluno que se cadastrar na nossa aplicação
    // vai ter um papel específico que vai dizer quais permissões ele possui
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "alunos_roles",
            joinColumns = @JoinColumn(name = "aluno_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RolesEntity> roles = new HashSet<>();

    // Aqui estamos dizendo ao Spring como ele deve reconhecer as autoridades do usuário (roles)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    // Basicamente, quando fazemos o fluxo de login o Spring vai usar o getUsername e getPassword para autenticar o usuário e quando
    // fazemos o login para sabermos se o usuário pode acessar o recurso ou não vamos usar o getAuthorities

    // Aqui estamos dizendo ao Spring como ele deve reconhecer uma senha do usuário
    @Override
    public @Nullable String getPassword() {
        return senha;
    }

    // Aqui estamos dizendo ao Spring como ele deve reconhecer o username do usuário
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
