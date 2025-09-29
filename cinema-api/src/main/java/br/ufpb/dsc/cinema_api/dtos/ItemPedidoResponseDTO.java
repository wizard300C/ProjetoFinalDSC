package br.ufpb.dsc.cinema_api.dtos;

import br.ufpb.dsc.cinema_api.models.Pedido;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemPedidoResponseDTO {
    private Long itemPedidoID;

    @Positive(message = "A quantidade do produto não pode ser negativa!")
    @NotNull(message = "A quantidade do produto não pode ser nula!")
    private Integer quantidade;

    @Positive(message = "O preço total não pode ser negativo!")
    @NotNull(message = "O preço total não pode ser nulo!")
    private Double precoTotal;

    @NotNull(message = "O produto não pode ser nulo!")
    private ProdutoDTO produto;

}
