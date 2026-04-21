package dev.fabiokusaba.essentials.controller;

import dev.fabiokusaba.essentials.dto.AlunoRequestDto;
import dev.fabiokusaba.essentials.dto.AvaliacaoFisicaResponseDto;
import dev.fabiokusaba.essentials.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody AlunoRequestDto alunoRequestDto) {
        alunoService.save(alunoRequestDto);
    }

    @GetMapping("/{id}/avaliacao-fisica")
    public ResponseEntity<AvaliacaoFisicaResponseDto> getAvaliacaoFisicaByAlunoId(@PathVariable Long id) {
        var avaliacaoFisicaAluno = alunoService.getAvaliacaoFisicaByAlunoId(id);
        return ResponseEntity.ok(avaliacaoFisicaAluno);
    }
}
