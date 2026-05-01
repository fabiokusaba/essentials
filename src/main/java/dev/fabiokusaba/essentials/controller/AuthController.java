package dev.fabiokusaba.essentials.controller;

import dev.fabiokusaba.essentials.dto.LoginRequestDto;
import dev.fabiokusaba.essentials.dto.RegisterRequestDto;
import dev.fabiokusaba.essentials.dto.TokenResponseDto;
import dev.fabiokusaba.essentials.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        authenticationService.register(registerRequestDto);
    }

    @PostMapping("/login")
    public TokenResponseDto login(@Valid @RequestBody LoginRequestDto loginRequestDto) throws Exception {
        return authenticationService.login(loginRequestDto);
    }
}
