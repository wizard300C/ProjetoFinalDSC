package br.ufpb.dsc.cinema_api.dtos;

import br.ufpb.dsc.cinema_api.models.Sessao;
import br.ufpb.dsc.cinema_api.models.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IngressoDTO {

    private Long ingressoID;

    @Positive(message = "O preço do ingresso não pode ser negativo!")
    private Double preco;

    @NotBlank(message = "O assento não pode estar vazio!")
    @Size(min = 4, message = "O nome do assento não pode ter mais de 4 caractere")
    private String assento;

    private Usuario usuario;

    private Sessao sessao;

}
