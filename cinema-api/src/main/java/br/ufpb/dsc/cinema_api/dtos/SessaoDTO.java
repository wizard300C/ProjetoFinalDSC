package br.ufpb.dsc.cinema_api.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SessaoDTO {

    private Long sessaoID;

    @NotNull(message = "O horário da sessão não pode ser nulo!")
    private LocalDateTime horario;

    @NotNull(message = "A sala não pode ser nula!")
    private Long  salaID;

    @Positive
    private Double preco;
}
