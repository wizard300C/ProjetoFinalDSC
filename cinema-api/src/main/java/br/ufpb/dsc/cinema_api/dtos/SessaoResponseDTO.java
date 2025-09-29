package br.ufpb.dsc.cinema_api.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SessaoResponseDTO {
    private Long sessaoID;
    private LocalDateTime horario;
    private Double preco;
    private FilmeDTO filme;
    private SalaDTO sala;
}
