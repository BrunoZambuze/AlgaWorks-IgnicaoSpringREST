package com.algaworks.algatransito.api.controller;

import com.algaworks.algatransito.api.representationmodel.VeiculoRepresentationModel;
import com.algaworks.algatransito.domain.model.Veiculo;
import com.algaworks.algatransito.domain.repository.VeiculoRepository;
import com.algaworks.algatransito.domain.service.RegistroVeiculoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RequestMapping("/veiculos") //Irá informar que todos os endpoints desse controlodar começará com /veiculos
@RestController
public class VeiculoController {

    private final VeiculoRepository veiculoRepository;
    private final RegistroVeiculoService registroVeiculoService;
    private final ModelMapper modelMapper; //<--- Responsável por mapear o domain model em um representation model (isso facilita para não precisar
                                                                                                          //ficar repetindo códigos a cada método)
    /*
    Explicação: Para utilizar o ModelMapper precisamos adicionar uma dependência no nosso maven no .xml, porém o Model Mapper NÃO é um componente
    do Spring, ou seja, ele não conseguirá injetar uma nova instância através do Lombok. Para resolver esse problema, criamos uma pasta "common" ou
    "config", e dentro dela terá uma classe "ModelMaperConfig". Sempre que precisar de uma instância de ModelMapper, o Spring chamará o método
    modelMapper() para fornecer a instância.
     */

    @GetMapping
    public List<VeiculoRepresentationModel> listar(){
        return veiculoRepository.findAll()
                                .stream()
                                .map(veiculo -> modelMapper.map(veiculo, VeiculoRepresentationModel.class))
                                .collect(Collectors.toList());
    }

    @GetMapping("/{veiculoId}")
    public ResponseEntity<VeiculoRepresentationModel> buscar(@PathVariable Long veiculoId){
        return veiculoRepository.findById(veiculoId)      //vamos estar mapeando o veiculo para uma nova instancia de VeiculoRepresentationModel
                                                         //já com todas as propriedades preenchidas
                                .map(veiculo -> modelMapper.map(veiculo, VeiculoRepresentationModel.class))
                                .map(v -> ResponseEntity.ok(v))
                                .orElse(ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Veiculo cadastrar(@Valid @RequestBody Veiculo veiculo){
        return registroVeiculoService.cadastrar(veiculo);
    }

}
