package dev.fabiokusaba.essentials.controller;

import dev.fabiokusaba.essentials.dto.AvaliacaoFisicaRequestDto;
import dev.fabiokusaba.essentials.service.AvaliacaoFisicaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/avaliacoes-fisicas")
@RequiredArgsConstructor
@Validated
public class AvaliacaoFisicaController {

    private final AvaliacaoFisicaService avaliacaoFisicaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody AvaliacaoFisicaRequestDto avaliacaoFisicaRequestDto) throws Exception {
        avaliacaoFisicaService.save(avaliacaoFisicaRequestDto);
    }
}
