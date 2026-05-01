package dev.fabiokusaba.essentials.config;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecutiryConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // A primeira coisa que a gente faz em aplicações REST é desabilitar o csrf, geralmente para
                // aplicações que guardam estado de sesão. Em APIs REST é comum a gente utilizar stateless, ou
                // seja, não guardamos sessão, a gente valida cada requisição que chega toda vez do zero
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                        .accessDeniedHandler(((request, response, accessDeniedException) -> {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                        }))
                )
                .authorizeHttpRequests(a -> {
                    a.requestMatchers(HttpMethod.POST, "/v1/auth/**").permitAll()
                            // Quando usamos o hasRole o Spring vai buscar no banco uma role que comece com ROLE_<NOME>, exemplo: ROLE_USER
                            .requestMatchers(HttpMethod.POST, "/v1/avaliacoes").hasRole("ADMIN")
                            .anyRequest().authenticated();
                })
                // Por padrão o Spring executa UsernamePasswordAuthenticationFilter, mas queremos falar que antes dele executar esse filter ele
                // precisa executar o nosso filter antes (jwtAuthenticationFilter) porque no nosso filter validamos o token e definimos uma
                // autenticação para dentro do contexto do Spring Security com as informações do usuário, autoridades (roles) e uma vez definido
                // essas configurações ele faz o filter de autenticação dele mesmo
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
