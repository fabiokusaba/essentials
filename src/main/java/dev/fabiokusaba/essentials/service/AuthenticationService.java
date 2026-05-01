package dev.fabiokusaba.essentials.service;

import dev.fabiokusaba.essentials.config.TokenProvider;
import dev.fabiokusaba.essentials.database.model.AlunoEntity;
import dev.fabiokusaba.essentials.database.model.RolesEntity;
import dev.fabiokusaba.essentials.database.repository.IAlunoRepository;
import dev.fabiokusaba.essentials.database.repository.IRolesRepository;
import dev.fabiokusaba.essentials.dto.LoginRequestDto;
import dev.fabiokusaba.essentials.dto.RegisterRequestDto;
import dev.fabiokusaba.essentials.dto.TokenResponseDto;
import dev.fabiokusaba.essentials.enums.RoleTypeEnum;
import dev.fabiokusaba.essentials.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IAlunoRepository alunoRepository;
    private final IRolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Value("${jwt.expiration}")
    private Long expirationTime;

    public void register(RegisterRequestDto registerRequestDto) {
        AlunoEntity aluno = alunoRepository.findByEmail(registerRequestDto.email())
                .orElse(null);

        if (aluno != null) {
            throw new BadRequestException("Aluno já cadastrado com esse email");
        }

        RolesEntity role = rolesRepository.findByNome(RoleTypeEnum.ROLE_ALUNO.name())
                .orElseGet(() -> rolesRepository.save(RolesEntity.builder()
                                .nome(RoleTypeEnum.ROLE_ALUNO.name())
                        .build()));

        String passwordHash = passwordEncoder.encode(registerRequestDto.senha());

        alunoRepository.save(AlunoEntity.builder()
                        .nome(registerRequestDto.nome())
                        .email(registerRequestDto.email())
                        .roles(Set.of(role))
                        .senha(passwordHash)
                .build());
    }

    public TokenResponseDto login(LoginRequestDto loginRequestDto) throws Exception {
        try {
            // Validar usuário e senha em nossa base de dados
            // Por baixo dos panos o AuthenticationManager vai chamar o AuthenticationProvider -> UserDetailsService
            // que é a interface que a gente implementou o metodo loadUserByUsername para que ele possa carregar o
            // usuário a partir do email -> encontrou o usuário ele vai comparar se a senha é válida através do
            // PasswordEncoder -> senha válida ele vai considerar que você é um usuário autenticado e vai retornar
            // para nós uma Authentication
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequestDto.email(), loginRequestDto.senha()
            ));
            String token = tokenProvider.gerarToken(authentication);

            return new TokenResponseDto(token, expirationTime);
        } catch (BadCredentialsException e) {
            throw new BadRequestException("Credenciais inválidas");
        } catch (Exception e) {
            throw new Exception("Ocorreu um erro inesperado, tente novamente mais tarde");
        }
    }
}
