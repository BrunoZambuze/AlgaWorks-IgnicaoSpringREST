package com.algaworks.algatransito.api.representationmodel.output;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class AutuacaoReperesentationModel {

    private Long id;
    private String descricao;
    private BigDecimal valorMulta;
    private OffsetDateTime dataAutuacao;

}
