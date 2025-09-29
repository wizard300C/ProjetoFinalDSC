package br.ufpb.dsc.cinema_api.exception;

public class PedidoNotFoundException extends RuntimeException {
    public PedidoNotFoundException(String mensagem) {
        super(mensagem);
    }

}
