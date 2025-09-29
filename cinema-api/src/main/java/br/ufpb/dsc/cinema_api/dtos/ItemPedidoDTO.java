package br.ufpb.dsc.cinema_api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemPedidoDTO {
    @NotNull(message = "O ID do produto não pode ser nulo.")
    private Long produtoId;

    @NotNull(message = "A quantidade não pode ser nula.")
    @Min(value = 1, message = "A quantidade deve ser de no mínimo 1.") // @Min(1) é mais explícito que @Positive
    private Integer quantidade;
}

