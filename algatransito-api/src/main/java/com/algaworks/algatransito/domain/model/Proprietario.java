package com.algaworks.algatransito.domain.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //Sem o "onlyExplicitlyIncluded" estaremos implementando o Equals/HashCode para todos os atributos
                                                 //mas agora eu só estarei utilizando para os atributos que eu declarar explícitamente
@Entity //Uma entidade está automaticamente ligada a uma tabela do banco de dados, se o nome de ambos estiverem iguais, podemos manter só com o Entity
//@Table(name = "proprietario") Caso contrário, precisamos indicar qual a tabela que a classe Proprietario está sendo mapeada
public class Proprietario {

    @EqualsAndHashCode.Include //Explícitamente estou incluindo o atributo id no Equals/HashCode
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nome;
    @Column
    private String email;
    @Column
    private String telefone;

    public Proprietario(){}

}
