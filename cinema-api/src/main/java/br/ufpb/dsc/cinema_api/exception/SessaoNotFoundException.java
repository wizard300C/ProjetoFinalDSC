package br.ufpb.dsc.cinema_api.exception;

public class SessaoNotFoundException extends RuntimeException {
    public SessaoNotFoundException(String mensagem) {
        super(mensagem);
    }
}
