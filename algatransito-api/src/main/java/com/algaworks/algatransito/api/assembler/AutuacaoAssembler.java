package com.algaworks.algatransito.api.assembler;

import com.algaworks.algatransito.api.representationmodel.input.AutuacaoInput;
import com.algaworks.algatransito.api.representationmodel.output.AutuacaoReperesentationModel;
import com.algaworks.algatransito.domain.model.Autuacao;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class AutuacaoAssembler {

    private final ModelMapper modelMapper;

    public Autuacao toAutuacao(AutuacaoInput autuacaoInput){
        return modelMapper.map(autuacaoInput, Autuacao.class);
    }

    public AutuacaoReperesentationModel toRepresentationModel(Autuacao autuacao){
        return modelMapper.map(autuacao, AutuacaoReperesentationModel.class);
    }

    public List<AutuacaoReperesentationModel> toCollectionModel(List<Autuacao> autuacoes){
        return autuacoes.stream()
                        .map(autucao -> modelMapper.map(autucao, AutuacaoReperesentationModel.class))
                        .toList();
    }

}
