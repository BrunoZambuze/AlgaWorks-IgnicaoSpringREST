package com.algaworks.algatransito.domain.service;

import com.algaworks.algatransito.domain.exception.RegraDeNegocioException;
import com.algaworks.algatransito.domain.model.Proprietario;
import com.algaworks.algatransito.domain.repository.ProprietarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service //Informamos que essa classe é um componente do Spring, assim ele olhará esse classe não somente como um componente qualquer, mas sim como um
        //componente relacionado com o serviço (regra de negócio). Também poderemos injetar uma instância dessa classe em outras classes
public class RegistroProprietarioService {

    private final ProprietarioRepository proprietarioRepository;

    //Transactional possui 2 bibliotecas (Jakarta e SpringFramework), devemos escolher o SpringFramework
    @Transactional //Essa anotação informa que o método precisa ser executado dentro de uma transação, ou seja, caso algo dê errado no método, todas as
                  //Operações realizadas que envolvem banco de dados serão descartadas (Voltar ao que era antes)
    public Proprietario salvar(Proprietario proprietario){

        boolean emailEmUso = proprietarioRepository.findByEmail(proprietario.getEmail())
                                                   .filter(p -> !p.equals(proprietario))
                                                   .isPresent();
        if(emailEmUso){
            throw new RegraDeNegocioException("Já existe um proprietário cadastrado com este e-mail!");
        }
        return proprietarioRepository.save(proprietario); //.save irá retornar o proprietario salvo.
                                                         //Se já existir esse proprietario o JPA vai substitui pelo novo,
                                                        //caso contrário ele irá criar um novo proprietario. Por isso que também
                                                       //podemos usar o .save tanto put quanto no post
    }

    @Transactional
    public void excluir(Long proprietarioId){
        proprietarioRepository.deleteById(proprietarioId);
    }

}
