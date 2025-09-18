package br.ufpb.dsc.cinema_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SalaDTO {

    private Long salaID;

    @NotBlank(message = "O nome da sala não pode ser vazia!")
    @Size(max = 30, message = "O nome da sala não pode ter mais de 30 caracteres!")
    private String nomeSala;

    @Positive(message = "A capacidade da sala não pode ser negativa!")
    private Integer capacidade;

}
