package com.algaworks.algatransito.domain.service;

import com.algaworks.algatransito.domain.model.Autuacao;
import com.algaworks.algatransito.domain.model.Veiculo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RegistroAutuacaoService {

    private RegistroVeiculoService registroVeiculoService;

    @Transactional
    public Autuacao registrar(Long veiculoId, Autuacao novaAutuacao){
        Veiculo novoVeiculo = registroVeiculoService.buscar(veiculoId);
        return novoVeiculo.adicionarAutuacao(novaAutuacao);
    }

}
