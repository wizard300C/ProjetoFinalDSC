package br.ufpb.dsc.cinema_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProdutoDTO {

    private Long produtoID;

    @NotBlank(message = "O nome do produto não pode ser vazio!")
    @Size(max = 50, message = "O nome do produto não pode ter mais de 50 caracteres!")
    @NotNull
    private String nomeProduto;

    @Positive(message = "O preço do produto não pode ser negativo!")
    @NotNull
    private Double preco;


}
