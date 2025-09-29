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
public class PedidoResponseDTO {
    private Long pedidoID;

    @NotNull
    private LocalDateTime data;

    @NotNull
    private UsuarioPedidoDTO usuario;

    @NotNull
    private Collection<ItemPedidoResponseDTO> itens;
}
