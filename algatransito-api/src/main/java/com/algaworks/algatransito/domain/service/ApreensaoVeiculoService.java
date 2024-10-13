package com.algaworks.algatransito.domain.service;

import com.algaworks.algatransito.domain.model.StatusVeiculo;
import com.algaworks.algatransito.domain.model.Veiculo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ApreensaoVeiculoService {

    /*
    Criamos esse Service para separar a responsabilidade, pois queremos que a classe "RegistroVeiculoService" tenha somente a função de registro de veículso e não
    se preocupar com regras de negócios de apreensão.
     */

    private final RegistroVeiculoService registroVeiculoService;

    @Transactional
    public void apreender(Long veiculoId){
        Veiculo veiculo = registroVeiculoService.buscar(veiculoId);
        veiculo.apreender();
    }

    @Transactional
    public void removerApreensao(Long veiculoId){
        Veiculo veiculo = registroVeiculoService.buscar(veiculoId);
        veiculo.removerApreensao();
    }

}
