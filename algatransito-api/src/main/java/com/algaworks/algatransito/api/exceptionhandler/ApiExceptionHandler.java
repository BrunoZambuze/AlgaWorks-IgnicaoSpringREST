package com.algaworks.algatransito.api.exceptionhandler;

import com.algaworks.algatransito.domain.exception.RegraDeNegocioException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

/*
Explicação: Todo controlador precisa tratar as exceções e uma maneira de trata-las é utilizando o ExceptionHandler, que irá tratar uma exceção específica,
porém a cada controlador teremos que criar diversos e até os mesmos tipos de exceções do ExceptionHandler, duplicando o código e fazendo com que as exceções
fiquem "espalhadas" pela aplicação, dificultando futuras manutenções. Para corrigir esse problema nós podemos criar uma única classe e tratar todas as exceções
em um único ponto do código.
 */

@RestControllerAdvice //Permite o tratamento das exceções de maneira global em toda a aplicação independente de qual controlador que lançou a exception
                 //ResponseEntityExceptionHandler: Irá formatar a mensagem da exceção na resposta da requisição com base no RFC 7807 (Problem Detail)
@AllArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    //Precisamos dessa injeção para poder utilizar a mensagem customizada que criamos no "messages.properties"
    private final MessageSource messageSource;

    /*
    Eplicação: Quando temos um erro de validação o @Valid irá lançar uma exceção MethodArgumentNotValidException, o @RestControllerAdvice irá capturar
    essa exceção e como estamos herdando da classe ResponseEntityExceptionHandler, ela possui um método que irá verificar se essa exceção que nós estamos
    capturando é uma instância de MethodArgumentNotValidException, se for, esse método irá chamar um outro método chamado handleMethodArgumentNotValid.
    Esse método será capaz de tratar essa exceção retornando a formatação do RFC 7807 (Problem Detail).
    Porém nós podemos personalizar a resposta do Problem Detail, como estamos herdando os métodos da classe ResponseEntityExceptionHandler, nós podemos
    sobrescrever esse método com as nossas configurações de mensagem.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        //Vamos criar um problem detail e instanciar um status para ele, o status está disponível no nosso parâmetro e esse parâmetro
        // irá receber o BadRequest por padrão, vamos manter assim
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);

        //****** Agora que já temos nosso problem detail, vamos customizar as mensagens ******
        problemDetail.setTitle("Um ou mais campos estão inválidos");
        problemDetail.setType(URI.create("https://algatransito.com/erros/campos-invalidos")); //URI que nos ajuda a identificar a origem do erro

        //****** Adicionando Campos Customizados no Problem Detail
        //getAllErrors: Irá retornar uma lista com todos as propriedades(campos) que possuem algum erro
        Map<String, String> fields = ex.getBindingResult().getAllErrors()
                             .stream()                  //getField: pegar o nome do campo
                             .collect(Collectors.toMap(objectError -> ((FieldError) objectError).getField(),
                                                      objectError -> messageSource.getMessage(objectError, LocaleContextHolder.getLocale())));
                        //messageSource: Irá pegar a mensagem do erro do OjbjectError. Nesse momento o programa irá procurar o arquivo "messages.properties"
                       //para alterar as mensagens padrões para as personalizadas
        problemDetail.setProperty("invalidFields",fields);

        //Vamos fazer o método retornar handleExceptionInternal e passar a exceção que recebemos e o corpo da resposta da requisição, que no caso seria o
        //problemDetail customizado, de resto nós só passamos o restante dos argumentos
        return handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    @ExceptionHandler(RegraDeNegocioException.class)
    public ProblemDetail handleRegraDeNegocioException(RegraDeNegocioException e){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle(e.getMessage());
        problemDetail.setType(URI.create("https://algatransito.com/erros/violacao-regra-de-negocio"));
        return problemDetail;
    }

    //DataIntegrityViolationException: Quando estamos querendo remover um recurso que está sendo utilizado em outra tabela (fk_key)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException e){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        problemDetail.setTitle("Recurso está em uso!");
        problemDetail.setType(URI.create("https://algatransito.com/erros/conflito-de-recurso"));
        return problemDetail;
    }

}
