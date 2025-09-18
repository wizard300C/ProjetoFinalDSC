package br.ufpb.dsc.cinema_api.exception;

public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException(String mensagem) {
        super(mensagem);
    }
}
