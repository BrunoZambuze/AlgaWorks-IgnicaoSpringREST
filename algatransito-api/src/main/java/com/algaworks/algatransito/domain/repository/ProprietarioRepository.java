package com.algaworks.algatransito.domain.repository;

import com.algaworks.algatransito.domain.model.Proprietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //Avisamos para o Spring que essa interface atual é um repositório. assim ele consegue entender o contexto da aplicação.
                                                     //Proprietario: Tipo de entidade que o repositório vai manipular
                                                    //Long: Tipo do identificador (Chave primária)
public interface ProprietarioRepository extends JpaRepository<Proprietario, Long> {
    //A interface irá fazer um contrato com o Data JPA onde ele irá implementar os métodos de persistência do JpaRepository nessa interface em tempo de execução.

    List<Proprietario> findByNomeContaining(String nome);

}
