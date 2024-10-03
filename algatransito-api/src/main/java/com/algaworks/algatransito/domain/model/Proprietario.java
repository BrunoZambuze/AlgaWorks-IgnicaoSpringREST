package com.algaworks.algatransito.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank //Anotação do Jakarta Bean Validation que verifica se o objeto não é nulo, vazio, em branco, etc... Ele permite que não estoure uma exceção
             //de integração com o banco de dados, pois no MySql as colunas são NotNull
    @Size(max = 60) //No banco de dados a coluna nome possui um limite de 60 caracteres, essa anotação vai validar se o nome tem a quantidade de caracteres necessário
    private String nome;

    @Column
    @NotBlank
    @Size(max = 255)
    @Email //Irá validar se o formato do email está correto
    private String email;

    @Column
    @NotBlank
    @Size(max = 11)
    private String telefone;

}
