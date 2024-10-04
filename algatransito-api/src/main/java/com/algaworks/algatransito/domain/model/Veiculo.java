package com.algaworks.algatransito.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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
    @NotBlank
    private Proprietario proprietario;

    @Column
    @NotBlank
    @Size(max = 20)
    private String marca;

    @Column
    @NotBlank
    @Size(max = 20)
    private String modelo;

    @Column
    @NotBlank
    @Size(max = 7)
    private String placa;

    @Column
    @NotBlank
    @Enumerated(EnumType.STRING) //Essa anotação permite escolhermos se queremos retornar o numero do Enum ou o que está escrito no enum. Nesse caso
                                //queremos as escritas "REGULAR" e "APREENDIDO"
    private StatusVeiculo status;

    @Column(name = "data_cadastro")
    @NotBlank
    private LocalDateTime dataCadastro;

    @Column(name = "data_apreensao")
    private LocalDateTime dataApreensao;

}
