package com.algaworks.algatransito.domain.model;

import com.algaworks.algatransito.domain.validation.ValidationGroups;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne //Anotação que informa que muitos veiculos tem somente 1 proprietario
    @JoinColumn(name = "proprietario_id") //Especifica qual a coluna que faz o relacionamento com a tabela proprietario
    @Valid //No controller do veiculo a validação só é feita nos atributos de veiculos. Caso nós quisermos fazer a validação nos atributos de outra classe,
          //no nosso caso a classe Proprietario que possui suas próprias validação, como id, nome, etc... nós devemos usar a anotação @Valid

    @ConvertGroup(from = Default.class, to = ValidationGroups.ProprietarioId.class) //Na hora de fazer a validação, o Spring não irá validar o proprietario com
                 //grupo padrão(Default) das anotações @Notnull, ele irá sair do grupo (from) Padrão e ir para o grupo (to) do ProprietarioId, ou seja, será
                // feita a validação somente do atributo com a anotação do (groups = ValidationGroups.ProprietarioId.class). Nesse caso só será feita a validação no id
    @NotNull

    /*
    Percurso do programa: O controlador irá acionar a validação do veiculo, será feita toda a validação das anotações com o grupo Padrão (Default), o Spring
    irá ver que temos uma validação para fazer (@NotNull) no nosso atributo proprietário, pronto, agora o veiculo não pode ter o proprietario nulo, porém o
    Spring viu que temos uma anotação de validação de cascata (@Valid), ou seja, vamos fazer a validação da classe Proprietario, mas antes de validar, o
    Spring irá ver que temos uma outra anotação dizendo que essa validação não será feita no grupo de validação padrão, mas sim no grupo de validação do
    proprietarioId (@ConvertGroup), com isso o Spring irá fazer a validação somente do atributo id, pois ele é o único que possui a anotação do grupo proprietarioId.
     */

    private Proprietario proprietario;

    @Column
    @NotBlank //<-- utilizado somente para String
    @Size(max = 20)
    private String marca;

    @Column
    @NotBlank
    @Size(max = 20)
    private String modelo;

    @Column
    @NotBlank
    @Size(max = 7)
    //Temos 2 padrões de placa: XXX0000 e XXX0X00 | Vamos fazer a validação desse padrão utilizando o jakarta bean validation e Expressões regulares
    @Pattern(regexp = "[A-Z]{3}[0-9]{1}[0-9A-Z]{1}[0-9]{2}")
    private String placa;

    @Column
    @Enumerated(EnumType.STRING) //Essa anotação permite escolhermos se queremos retornar o numero do Enum ou o que está escrito no enum. Nesse caso
                                //queremos as escritas "REGULAR" e "APREENDIDO"
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) //Essa anotação informa que esse atributo não poderá ser atribuído valores no corpo da requisição,
                                                         //somente será realizado a leitura dele. Ou seja, se tentarmos inserir algum valor desses atributos
                                                        //diretamente no corpo da requisição, essa inserção será ignorada.
    private StatusVeiculo status;

    @Column(name = "data_cadastro")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataCadastro;

    @Column(name = "data_apreensao")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime dataApreensao;

}