package br.ufpb.dsc.cinema_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegistroException extends RuntimeException {
    public RegistroException(String mensagem) {
        super(mensagem);
    }
}
