package br.ufpb.dsc.cinema_api.dtos;

import br.ufpb.dsc.cinema_api.models.Produto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemPedidoDTO {
    private Long itemPedidoID;

    @Positive(message = "A quantidade do produto não pode ser negativa!")
    private Integer quantidade;

    @Positive(message = "O preço total não pode ser negativo!")
    private Double precoTotal;

    @NotNull(message = "O produto não pode ser nulo!")
    private Produto produto;
}
