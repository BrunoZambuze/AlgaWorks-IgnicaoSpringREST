package com.algaworks.algatransito.domain.repository;

import com.algaworks.algatransito.domain.model.Proprietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository //Avisamos para o Spring que essa interface atual é um repositório. assim ele consegue entender o contexto da aplicação. Ou seja, estamos avisando o
//Spring que essa classe é um componente do Spring, mas não um componente qualquer, é um repositório, assim além do spring enteder o contexto da aplicação, nós
//poderemos injetar uma instancia dessa classe em outra classe (fazemos isso na classe controller, onde injetamos essa classe através do construtor do lombok)
                                                     //Proprietario: Tipo de entidade que o repositório vai manipular
                                                    //Long: Tipo do identificador (Chave primária)
public interface ProprietarioRepository extends JpaRepository<Proprietario, Long> {
    //A interface irá fazer um contrato com o Data JPA onde ele irá implementar os métodos de persistência do JpaRepository nessa interface em tempo de execução.

    List<Proprietario> findByNomeContaining(String nome);
    Optional<Proprietario> findByEmail(String email);
    //Se algo pode ou não ter, usamos o Optional

}
