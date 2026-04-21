package dev.fabiokusaba.essentials.dto;

import java.math.BigDecimal;

public interface AvaliacaoFisicaProjection {

    Long getIdAluno();
    String getNomeAluno();
    Long getIdAvaliacao();
    BigDecimal getPeso();
    BigDecimal getAltura();
    BigDecimal getPorcentagemGorduraCorporal();
}
