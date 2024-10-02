package com.algaworks.algatransito.api.controller;

import com.algaworks.algatransito.domain.model.Proprietario;
import com.algaworks.algatransito.domain.repository.ProprietarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/proprietarios")
public class ProprietarioController {

    private final ProprietarioRepository proprietarioRepository;

    @GetMapping
    public List<Proprietario> listarProprietarios(){
        /*
        Normalmente quando vamos utilizar uma interface, precisamos instanciar uma outra classe ou interface para poder
        utilizar a interface com a sua inplementação. Nesse caso, nós não precisamos fazer isso, pois o Spring Data JPA fornece
        uma implementação para nossa interface automáticamente. Isso não ocorre em código (tempo de compilação), mas sim
        através de um mecanismo interno do Spring , realizando uma injeção de dependência em tempo de execução. Assim podemos utilizar
        a variável do repositório diretamente, sem precisar gerar uma instância manualmente.
     */
//        return proprietarioRepository.findByNomeContaining("Marcelo");
        return proprietarioRepository.findAll();
    }

               //Variável de caminho
    @GetMapping("/{proprietarioId}")
    public ResponseEntity<Proprietario> buscar(@PathVariable Long proprietarioId){ //Vamos vincular esse parâmetro com a variável de caminho
        return proprietarioRepository.findById(proprietarioId)
                                     .map(ResponseEntity::ok)
                                     .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //Se tudo der certo irá retornar um status http 201 created em vez do 200 ok
    public Proprietario adicionar(@RequestBody Proprietario proprietario){ //Isso irá vincular esse parâmetro com o corpo da requisição. Lá será capturado os
                                                                          //dados em formato JSON, será desserializado e transformado em um objeto Proprietario
        return proprietarioRepository.save(proprietario); //.save irá retornar o proprietario salvo
    }

    @PutMapping("/{proprietarioId}")
    public ResponseEntity<Proprietario> atualizar(@PathVariable Long proprietarioId,
                                                  @RequestBody Proprietario proprietario){
        if(!proprietarioRepository.existsById(proprietarioId)){ //existsById: método do Data JPA que verifica se existe um objeto com o id especificado
            return ResponseEntity.notFound().build();
        }
        proprietario.setId(proprietarioId);
        Proprietario proprietarioAtualizado = proprietarioRepository.save(proprietario); //Se já existir esse proprietario o JPA vai substitui pelo novo,
                                                                                        //caso contrário ele irá criar um novo proprietario. Por isso que também
                                                                                       //podemos usar o .save tanto put quanto no post
        return ResponseEntity.ok(proprietarioAtualizado);
    }

    @DeleteMapping("/{proprietarioId}")
    public ResponseEntity<Void> deletar(@PathVariable Long proprietarioId){
        if(!proprietarioRepository.existsById(proprietarioId)){
            return ResponseEntity.notFound().build();
        }
        proprietarioRepository.deleteById(proprietarioId);
        return ResponseEntity.noContent().build();
    }

}