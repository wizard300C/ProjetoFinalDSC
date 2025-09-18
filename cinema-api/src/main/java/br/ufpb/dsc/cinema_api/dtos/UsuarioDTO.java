package br.ufpb.dsc.cinema_api.dtos;

import br.ufpb.dsc.cinema_api.models.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDTO {

    private Long usuarioID;

    @NotBlank(message = "O nome não pode estar vazio!")
    @Size(max = 125, message = "O nome não pode ter mais de 125 caracteres!")
    private String nome;

    @NotBlank(message = "O nome do usuário não pode estar vazio!")
    @Size(max = 30, message = "O nome do usuário não pode ter mais de 30 caracteres!")
    private String nomeUsuario;

    @NotBlank(message = "O email não pode estar vazio!")
    @Email(message = "O email precisa ser válido!")
    private String email;

    @Size(min = 8, max = 24, message = "A senha precisa não pode ter menos de 8 e mais que 24 caracteres")
    @NotBlank(message = "A senha não pode estar vazia!")
    private String senha;

    @NotNull(message = "O papel do usuário não pode ser nulo!")
    private Role role;

}
