package com.algaworks.algatransito.api.controller;

import com.algaworks.algatransito.domain.exception.RegraDeNegocioException;
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

    @GetMapping
    public List<Veiculo> listar(){
        return veiculoRepository.findAll();
    }

    @GetMapping("/{veiculoId}")
    public ResponseEntity<Veiculo> buscar(@PathVariable Long veiculoId){
        return veiculoRepository.findById(veiculoId)
                                .map(v -> ResponseEntity.ok(v))
                                .orElse(ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Veiculo cadastrar(@RequestBody Veiculo veiculo){
        return registroVeiculoService.cadastrar(veiculo);
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<String> capturar(RegraDeNegocioException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
