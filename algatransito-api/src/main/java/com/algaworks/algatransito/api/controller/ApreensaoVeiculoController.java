package com.algaworks.algatransito.api.controller;

import com.algaworks.algatransito.domain.service.ApreensaoVeiculoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/veiculos")
public class ApreensaoVeiculoController {

    private final ApreensaoVeiculoService apreensaoVeiculoService;

    @PutMapping("/{veiculoId}/apreensao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void apreender(@PathVariable Long veiculoId){
        apreensaoVeiculoService.apreender(veiculoId);
    }

    @DeleteMapping("/{veiculoId}/apreensao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerApreensao(@PathVariable Long veiculoId){
        apreensaoVeiculoService.removerApreensao(veiculoId);
    }

}
