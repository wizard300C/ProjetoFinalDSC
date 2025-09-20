package br.ufpb.dsc.cinema_api.dtos;

import br.ufpb.dsc.cinema_api.models.Filme;
import br.ufpb.dsc.cinema_api.models.Sala;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SessaoDTO {

    private Long sessaoID;

    @NotNull(message = "O horário da sessão não pode ser nulo!")
    private LocalDateTime horario;

    @NotNull(message = "O filme não pode ser nulo!")
    private Filme filme;

    @NotNull(message = "A sala não pode ser nula!")
    private Sala sala;
}
