package com.github.lucasdevrj.excecoes;

public class ErroCepInvalidoException extends RuntimeException {

    private String mensagem;

    public ErroCepInvalidoException(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public String getMessage() {
        return mensagem;
    }
}
