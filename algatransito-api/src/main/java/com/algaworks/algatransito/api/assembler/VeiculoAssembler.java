package com.algaworks.algatransito.api.assembler;

import com.algaworks.algatransito.api.representationmodel.VeiculoRepresentationModel;
import com.algaworks.algatransito.domain.model.Veiculo;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component //Informar que essa classe é um componente do Spring
public class VeiculoAssembler {

    private final ModelMapper modelMapper;

    public VeiculoRepresentationModel toRepresentationModel(Veiculo veiculo){
        return modelMapper.map(veiculo, VeiculoRepresentationModel.class);
    }

    public List<VeiculoRepresentationModel> toCollectionRepresentationModel(List<Veiculo> veiculosLista){
        return veiculosLista.stream()
                            .map(veiculo -> this.toRepresentationModel(veiculo))
                            .toList();
    }

}
