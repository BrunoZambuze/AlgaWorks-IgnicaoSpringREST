package com.algaworks.algatransito.api.controller;

import com.algaworks.algatransito.api.assembler.AutuacaoAssembler;
import com.algaworks.algatransito.api.representationmodel.input.AutuacaoInput;
import com.algaworks.algatransito.api.representationmodel.output.AutuacaoReperesentationModel;
import com.algaworks.algatransito.domain.model.Autuacao;
import com.algaworks.algatransito.domain.model.Veiculo;
import com.algaworks.algatransito.domain.service.RegistroAutuacaoService;
import com.algaworks.algatransito.domain.service.RegistroVeiculoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/veiculos/{veiculoId}/autuacoes")
public class AutuacaoController {

    private final RegistroAutuacaoService registroAutuacaoService;
    private final AutuacaoAssembler autuacaoAssembler;
    private final RegistroVeiculoService registroVeiculoService;

    @GetMapping
    public List<AutuacaoReperesentationModel> listar(@PathVariable Long veiculoId){
        Veiculo veiculo = registroVeiculoService.buscar(veiculoId);
        return autuacaoAssembler.toCollectionModel(veiculo.getAutuacoes());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AutuacaoReperesentationModel registrar(@PathVariable Long veiculoId,
                              @Valid @RequestBody AutuacaoInput autuacaoInput){
        Autuacao novaAutuacao = registroAutuacaoService.registrar(veiculoId, autuacaoAssembler.toAutuacao(autuacaoInput));
        return autuacaoAssembler.toRepresentationModel(novaAutuacao);
    }

}
