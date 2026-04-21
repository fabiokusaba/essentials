package dev.fabiokusaba.essentials.controller;

import dev.fabiokusaba.essentials.dto.TreinoRequestDto;
import dev.fabiokusaba.essentials.service.TreinoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/treinos")
@RequiredArgsConstructor
@Validated
public class TreinoController {

    private final TreinoService treinoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody TreinoRequestDto treinoRequestDto) {
        treinoService.save(treinoRequestDto);
    }
}
