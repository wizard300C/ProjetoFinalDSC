package br.ufpb.dsc.cinema_api.dtos;

import br.ufpb.dsc.cinema_api.models.Sessao;
import br.ufpb.dsc.cinema_api.models.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IngressoDTO {

    private Long ingressoID;

    @Positive(message = "O preço do ingresso não pode ser negativo!")
    @NotNull
    private Double preco;

    @NotBlank(message = "O assento não pode estar vazio!")
    @Size(min = 4, message = "O nome do assento não pode ter mais de 4 caracteres")
    @NotNull
    private String assento;

    @NotNull(message = "O usuário não pode ser nulo!")
    private Usuario usuario;

    @NotNull(message = "A sessão não pode ser nula!")
    private Sessao sessao;

}
