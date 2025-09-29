package br.ufpb.dsc.cinema_api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioPedidoDTO {
    private Long usuarioID;

    @NotBlank(message = "O nome não pode estar vazio!")
    @Size(max = 125, message = "O nome não pode ter mais de 125 caracteres!")
    @NotNull
    private String nome;

    @NotBlank(message = "O nome do usuário não pode estar vazio!")
    @Size(max = 30, message = "O nome do usuário não pode ter mais de 30 caracteres!")
    @NotNull
    private String nomeUsuario;


}
