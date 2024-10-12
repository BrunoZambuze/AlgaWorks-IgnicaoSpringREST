package com.algaworks.algatransito.api.controller;

import com.algaworks.algatransito.api.assembler.VeiculoAssembler;
import com.algaworks.algatransito.api.representationmodel.output.VeiculoRepresentationModel;
import com.algaworks.algatransito.api.representationmodel.input.VeiculoRepresentationInput;
import com.algaworks.algatransito.domain.model.Veiculo;
import com.algaworks.algatransito.domain.repository.VeiculoRepository;
import com.algaworks.algatransito.domain.service.RegistroVeiculoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/veiculos") //Irá informar que todos os endpoints desse controlodar começará com /veiculos
@RestController
public class VeiculoController {

    private final VeiculoRepository veiculoRepository;
    private final RegistroVeiculoService registroVeiculoService;
    //Não é bom termos uma dependência direta do controller co mo ModelMapper, com isso, criamos a classe veiculoAssembler para poder fazer as transformações
    //do domain model para o representation model
    private final VeiculoAssembler veiculoAssembler;
    /*
    Explicação: Para utilizar o ModelMapper precisamos adicionar uma dependência no nosso maven no .xml, porém o Model Mapper NÃO é um componente
    do Spring, ou seja, ele não conseguirá injetar uma nova instância através do Lombok. Para resolver esse problema, criamos uma pasta "common" ou
    "config", e dentro dela terá uma classe "ModelMaperConfig". Sempre que precisar de uma instância de ModelMapper, o Spring chamará o método
    modelMapper() para fornecer a instância.
     */

    @GetMapping
    public List<VeiculoRepresentationModel> listar(){
        return veiculoAssembler.toCollectionRepresentationModel(veiculoRepository.findAll());
    }

    @GetMapping("/{veiculoId}")
    public ResponseEntity<VeiculoRepresentationModel> buscar(@PathVariable Long veiculoId){
        return veiculoRepository.findById(veiculoId)      //vamos estar mapeando o veiculo para uma nova instancia de VeiculoRepresentationModel
                                                         //já com todas as propriedades preenchidas
                                .map(veiculo -> veiculoAssembler.toRepresentationModel(veiculo))
                                .map(v -> ResponseEntity.ok(v))
                                .orElse(ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public VeiculoRepresentationModel cadastrar(@Valid @RequestBody VeiculoRepresentationInput veiculoInput){
        Veiculo novoVeiculo = veiculoAssembler.toVeiculo(veiculoInput);
        return veiculoAssembler.toRepresentationModel(registroVeiculoService.cadastrar(novoVeiculo));
    }

}
