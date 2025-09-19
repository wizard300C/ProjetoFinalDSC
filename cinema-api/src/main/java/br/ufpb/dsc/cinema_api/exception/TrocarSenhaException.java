package br.ufpb.dsc.cinema_api.exception;

public class TrocarSenhaException extends RuntimeException {
    public TrocarSenhaException(String mensagem) {
        super(mensagem);
    }
}
