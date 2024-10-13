package com.algaworks.algatransito.domain.exception;

public class EntidadeNaoEncontradaException extends RegraDeNegocioException{
    public EntidadeNaoEncontradaException(String msg){
      super(msg);
    }
}
