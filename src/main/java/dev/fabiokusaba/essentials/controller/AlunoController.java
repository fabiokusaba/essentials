package dev.fabiokusaba.essentials.controller;

import dev.fabiokusaba.essentials.dto.AlunoRequestDto;
import dev.fabiokusaba.essentials.dto.AvaliacaoFisicaResponseDto;
import dev.fabiokusaba.essentials.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/alunos")
@RequiredArgsConstructor
@Validated
public class AlunoController {

    private final AlunoService alunoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody AlunoRequestDto alunoRequestDto) {
        alunoService.save(alunoRequestDto);
    }

    // Com essa anotação estou indicando que o usuário logado somente pode ver as suas próprias informações não
    // permitindo que ele veja informações de outros alunos, mas também garantimos que um usuário ADMIN consiga
    // ver as avaliações de todos os alunos
    // Quando essa validação falhar ela vai nos retornar um error Access Denied
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    @GetMapping("/{id}/avaliacao-fisica")
    public ResponseEntity<AvaliacaoFisicaResponseDto> getAvaliacaoFisicaByAlunoId(@PathVariable Long id) {
        var avaliacaoFisicaAluno = alunoService.getAvaliacaoFisicaByAlunoId(id);
        return ResponseEntity.ok(avaliacaoFisicaAluno);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        alunoService.remove(id);
    }
}
