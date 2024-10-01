package com.algaworks.algatransito.api.controller;

import com.algaworks.algatransito.domain.model.Proprietario;
import com.algaworks.algatransito.domain.repository.ProprietarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class ProprietarioController {

    private final ProprietarioRepository proprietarioRepository;

    @GetMapping("/proprietarios")
    public List<Proprietario> listarProprietarios(){
        /*
        Normalmente quando vamos utilizar uma interface, precisamos instanciar uma outra classe ou interface para poder
        utilizar a interface com a sua inplementação. Nesse caso, nós não precisamos fazer isso, pois o Spring Data JPA fornece
        uma implementação para nossa interface automáticamente. Isso não ocorre em código (tempo de compilação), mas sim
        através de um mecanismo interno do Spring , realizando uma injeção de dependência em tempo de execução. Assim podemos utilizar
        a variável do repositório diretamente, sem precisar gerar uma instância manualmente.
     */
        return proprietarioRepository.findAll();
    }

}
