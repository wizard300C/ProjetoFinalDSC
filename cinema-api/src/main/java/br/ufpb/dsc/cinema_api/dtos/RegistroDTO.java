package br.ufpb.dsc.cinema_api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegistroDTO {

    @NotBlank(message = "O nome não pode estar vazio!")
    @Size(max = 125, message = "O nome não pode ter mais de 125 caracteres!")
    @NotNull
    private String nome;

    @NotBlank(message = "O nome de usuário não pode estar vazio!")
    @Size(max = 25, message = "O nome de usuário não pode ter mais de 25 caracteres!")
    @NotNull
    private String nomeUsuario;

    @NotBlank(message = "O email não pode estar vazio!")
    @Email
    @NotNull
    private String email;

    @NotBlank(message = "A senha não pode estar vazia!")
    @Size(min = 8, max = 24, message = "A senha precisa ter no mínimo 8 caracteres e no máximo 24!")
    @NotNull
    private String senha;
}
