package com.algaworks.algatransito.api.exceptionhandler;

import com.algaworks.algatransito.domain.exception.RegraDeNegocioException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/*
Explicação: Todo controlador precisa tratar as exceções e uma maneira de trata-las é utilizando o ExceptionHandler, que irá tratar uma exceção específica,
porém a cada controlador teremos que criar diversos e até os mesmos tipos de exceções do ExceptionHandler, duplicando o código e fazendo com que as exceções
fiquem "espalhadas" pela aplicação, dificultando futuras manutenções. Para corrigir esse problema nós podemos criar uma única classe e tratar todas as exceções
em um único ponto do código.
 */

@RestControllerAdvice //Permite o tratamento das exceções de maneira global em toda a aplicação independente de qual controlador que lançou a exception
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RegraDeNegocioException.class)
    public ResponseEntity<String> capturar(RegraDeNegocioException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
