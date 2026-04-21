package dev.fabiokusaba.essentials.database.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "avaliacoes_fisicas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// Utilizar a anotação JsonIgnoreProperties não é a melhor forma de tratar, mas para fins de conhecimento é
// possível usá-la para eliminarmos o campo hibernateLazyInitializer do nosso JSON de retorno
@JsonIgnoreProperties("hibernateLazyInitializer")
public class AvaliacaoFisicaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private BigDecimal peso;
    @Column(nullable = false)
    private BigDecimal altura;
    @Column(name = "porcentagem_gordura_corporal", nullable = false)
    private BigDecimal porcentagemGorduraCorporal;
}
