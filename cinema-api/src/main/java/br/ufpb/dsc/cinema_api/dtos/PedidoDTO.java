package br.ufpb.dsc.cinema_api.dtos;

import br.ufpb.dsc.cinema_api.models.ItemPedido;
import br.ufpb.dsc.cinema_api.models.Usuario;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@NoArgsConstructor
public class PedidoDTO {

    private Long pedidoID;

    @NotNull
    private LocalDateTime data;

    @NotNull
    private Usuario usuario;

    @NotNull
    private Collection<ItemPedido> itensPedidos;
}
