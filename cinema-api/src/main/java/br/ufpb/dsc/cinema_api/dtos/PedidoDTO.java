package br.ufpb.dsc.cinema_api.dtos;

import br.ufpb.dsc.cinema_api.models.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PedidoDTO {

    private Long pedidoID;
    private LocalDateTime data;
    private Usuario usuario;
}
