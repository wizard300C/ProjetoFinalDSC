package br.ufpb.dsc.cinema_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlterarSenhaDTO {

    @NotBlank(message = "A senha atual não pode estar vazia!")
    private String senhaAtual;

    @NotBlank(message = "A nova senha не pode estar vazia!")
    @Size(min = 8, max = 24, message = "A nova senha precisa ter entre 8 e 24 caracteres")
    private String novaSenha;

    @NotBlank(message = "A confirmação da senha não pode estar vazia!")
    private String confirmacaoNovaSenha;
}
