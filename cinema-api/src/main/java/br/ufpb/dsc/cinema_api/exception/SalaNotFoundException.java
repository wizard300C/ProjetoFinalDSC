package br.ufpb.dsc.cinema_api.exception;

public class SalaNotFoundException extends RuntimeException{
    public SalaNotFoundException(String mensagem){
        super(mensagem);
    }
}
