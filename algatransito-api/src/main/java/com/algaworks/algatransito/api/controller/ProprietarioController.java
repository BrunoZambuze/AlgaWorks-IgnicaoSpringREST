package com.algaworks.algatransito.api.controller;

import com.algaworks.algatransito.domain.exception.RegraDeNegocioException;
import com.algaworks.algatransito.domain.model.Proprietario;
import com.algaworks.algatransito.domain.repository.ProprietarioRepository;
import com.algaworks.algatransito.domain.service.RegistroProprietarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/proprietarios")
public class ProprietarioController {

    private final RegistroProprietarioService proprietarioService;
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
    public Proprietario adicionar(@Valid @RequestBody Proprietario proprietario){ //RequestBody: irá vincular esse parâmetro com o corpo da requisição. Lá será capturado os
                                                                                 //dados em formato JSON, será desserializado e transformado em um objeto Proprietario
                                                                                //Valid: Anotação do Jakarta Bean Validation que irá fazer a validação do objeto
                                                                               //verificando as outras anotações da classe Proprietario, como exemplo @NotBlank
        return proprietarioService.salvar(proprietario);
    }

    @PutMapping("/{proprietarioId}")
    public ResponseEntity<Proprietario> atualizar(@PathVariable Long proprietarioId,
                                                  @Valid @RequestBody Proprietario proprietario){
        if(!proprietarioRepository.existsById(proprietarioId)){ //existsById: método do Data JPA que verifica se existe um objeto com o id especificado (Select)
            return ResponseEntity.notFound().build();
        }
        proprietario.setId(proprietarioId);
        Proprietario proprietarioAtualizado = proprietarioService.salvar(proprietario);
        return ResponseEntity.ok(proprietarioAtualizado);
    }

    @DeleteMapping("/{proprietarioId}")
    public ResponseEntity<Void> deletar(@PathVariable Long proprietarioId){
        if(!proprietarioRepository.existsById(proprietarioId)){
            return ResponseEntity.notFound().build();
        }
        proprietarioService.excluir(proprietarioId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(RegraDeNegocioException.class) //ExceptionHandler irá capturar e tratar todas as chamadas de uma exceção específica
    public ResponseEntity<String> capturar(RegraDeNegocioException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}