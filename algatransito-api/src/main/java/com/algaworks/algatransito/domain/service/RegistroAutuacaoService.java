package com.algaworks.algatransito.domain.service;

import com.algaworks.algatransito.domain.model.Autuacao;
import com.algaworks.algatransito.domain.model.Veiculo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class RegistroAutuacaoService {

    private final RegistroVeiculoService registroVeiculoService;

    @Transactional
    public Autuacao registrar(Long veiculoId, Autuacao novaAutuacao){
        Veiculo novoVeiculo = registroVeiculoService.buscar(veiculoId);
        return novoVeiculo.adicionarAutuacao(novaAutuacao); //Como estamos em um contexto transacional, o Spring irá fazer uma "persistência automática" no banco de dados
                                                           //sempre que for detectada alguma modificação... Por isso não precisamos criar um repositório de Autuacao.

                                                          //"Qualquer alteração será sincronizada automaticamente com o banco de dados ao final da transação,
                                                         //sem necessidade de chamar métodos explícitos de repositório para salvar"
    }

}
