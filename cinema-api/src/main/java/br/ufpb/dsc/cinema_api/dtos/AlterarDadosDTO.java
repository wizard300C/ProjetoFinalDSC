package br.ufpb.dsc.cinema_api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AlterarDadosDTO {

    @Size(max = 125, message = "O nome не pode ter mais de 125 caracteres!")
    @NotNull
    private String nome;

    @Size(max = 30, message = "O nome do usuário не pode ter mais de 30 caracteres!")
    @NotNull
    private String nomeUsuario;

    @Email(message = "O email precisa ser válido!")
    @NotNull
    private String email;
}
