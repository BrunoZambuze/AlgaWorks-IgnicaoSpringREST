package com.algaworks.algatransito.domain.service;

import com.algaworks.algatransito.domain.exception.RegraDeNegocioException;
import com.algaworks.algatransito.domain.model.Proprietario;
import com.algaworks.algatransito.domain.model.StatusVeiculo;
import com.algaworks.algatransito.domain.model.Veiculo;
import com.algaworks.algatransito.domain.repository.ProprietarioRepository;
import com.algaworks.algatransito.domain.repository.VeiculoRepository;
import com.algaworks.algatransito.domain.validation.ValidationGroups;
import jakarta.validation.Valid;
import jakarta.validation.groups.ConvertGroup;
import jakarta.validation.groups.Default;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@AllArgsConstructor
@Service
public class RegistroVeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final RegistroProprietarioService registroProprietarioService;

    public Veiculo buscar(Long veiculoId){
        return veiculoRepository.findById(veiculoId).orElseThrow(() -> new RegraDeNegocioException("Não foi encontrado nenhum veículo!"));
    }

    @Transactional
    public Veiculo cadastrar(Veiculo veiculo){

        //Verificar que estamos criando um veiculo novo (Onde para criar esse veículo nós não precisamos pasasr o id no copor da requisição), caso
        //o veiculo tenha um id, quer dizer que estamos inserindo um veiculo já existente e não queremos isso
        if(veiculo.getId() != null){
            throw new RegraDeNegocioException("Veiculo a ser cadastrado não deve possuir um código");
        }

        boolean placaEmUso = veiculoRepository.findByPlaca(veiculo.getPlaca())
                                              .filter(v -> !v.equals(veiculo))
                                              .isPresent();
        if(placaEmUso){
            throw new RegraDeNegocioException("Já existe um veículo cadastrado com essa placa!");
        }

        Proprietario proprietario = registroProprietarioService.encontreOuFalhe(veiculo.getProprietario().getId());

        veiculo.setProprietario(proprietario);
        veiculo.setStatus(StatusVeiculo.REGULAR);
        veiculo.setDataCadastro(OffsetDateTime.now());
        return veiculoRepository.save(veiculo);
    }

}
