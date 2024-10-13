package com.algaworks.algatransito.api.representationmodel.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoResumoModel {
    private ProprietarioResumoModel proprietario;
    private String marca;
    private String modelo;
    private String placa;

}
