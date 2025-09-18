package br.ufpb.dsc.cinema_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginUsuarioDTO {

    @NotBlank(message = "O nome de usuário não pode estar vazio!")
    private String nomeUsuario;

    @NotBlank(message = "A senha não pode estar vazia!")
    @Size(min = 8, message = "A senha precisa não pode ter menos de 8 e mais que 24 caracteres")
    private String senha;
}
