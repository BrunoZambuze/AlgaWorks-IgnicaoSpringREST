package com.algaworks.algatransito.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Autuacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @Column
    @NotBlank
    private String descricao;

    @Column
    @NotNull
    private BigDecimal valorMulta;

    @Column
    @NotNull
    private OffsetDateTime dataOcorrencia;

}
