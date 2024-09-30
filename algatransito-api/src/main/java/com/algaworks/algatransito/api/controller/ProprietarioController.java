package com.algaworks.algatransito.api.controller;

import com.algaworks.algatransito.domain.model.Proprietario;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ProprietarioController {

    @GetMapping("/proprietarios")
    public List<Proprietario> listarProprietarios(){
        var proprietario1 = new Proprietario();
        proprietario1.setId(1L);
        proprietario1.setNome("João");
        proprietario1.setEmail("joaosilva@algaworks.com");
        proprietario1.setTelefone("16 992839283");

        var proprietario2 = new Proprietario();
        proprietario2.setId(2L);
        proprietario2.setNome("Maria");
        proprietario2.setEmail("maria@algaworks.com");
        proprietario2.setTelefone("16 9985734893");

        return Arrays.asList(proprietario1, proprietario2);
    }

}
