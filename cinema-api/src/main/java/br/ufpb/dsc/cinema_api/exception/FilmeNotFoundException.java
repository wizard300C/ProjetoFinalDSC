package br.ufpb.dsc.cinema_api.exception;

public class FilmeNotFoundException extends RuntimeException {
    public FilmeNotFoundException(String mensagem) {
        super(mensagem);
    }
}
