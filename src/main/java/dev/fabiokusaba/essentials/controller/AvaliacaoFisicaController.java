package dev.fabiokusaba.essentials.controller;

import dev.fabiokusaba.essentials.dto.AvaliacaoFisicaProjection;
import dev.fabiokusaba.essentials.dto.AvaliacaoFisicaRequestDto;
import dev.fabiokusaba.essentials.service.AvaliacaoFisicaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<AvaliacaoFisicaProjection>> getAllAvaliacoes() {
        var avaliacoes = avaliacaoFisicaService.getAllAvaliacoes();
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/page/{page}/size/{size}")
    public ResponseEntity<Page<AvaliacaoFisicaProjection>> getAllAvaliacoesPageable(
            @PathVariable Integer page,
            @PathVariable Integer size) {
        var avaliacoesPageable = avaliacaoFisicaService.getAllAvaliacoesPageable(page, size);
        return ResponseEntity.ok(avaliacoesPageable);
    }
}
