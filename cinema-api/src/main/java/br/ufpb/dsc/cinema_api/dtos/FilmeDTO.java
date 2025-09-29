package br.ufpb.dsc.cinema_api.dtos;

import br.ufpb.dsc.cinema_api.models.enums.ClassificacaoIndicativa;
import br.ufpb.dsc.cinema_api.models.enums.Genero;
import br.ufpb.dsc.cinema_api.validation.DuracaoValida;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FilmeDTO {
    private Long filmeID;

    @NotBlank(message = "o nome do filme não pode estar vazio!")
    @Size(max = 30, message = "O nome do filme não pode ter mais de 30 caracteres!")
    @NotNull
    private String nomeFilme;

    @NotBlank(message = "A sinopse do filme não pode estar vazia!")
    @Size(max = 50, message = "A sinopse não pode ter mais de 50 caracteres!")
    @NotNull
    private String sinopse;

    @NotNull(message = "O gênero do filme não pode ser nulo!")
    private Genero genero;

    @NotNull(message = "A duração é obrigatória")
    @Min(value = 1, message = "A duração mínima deve ser de 1 minuto")
    @Max(value = 200, message = "A duração máxima deve ser de 200 minutos")
    @DuracaoValida
    private Integer duracao;

    @NotNull(message = "A classificação indicativa não pode ser nula!")
    private ClassificacaoIndicativa classificacaoIndicativa;

}
