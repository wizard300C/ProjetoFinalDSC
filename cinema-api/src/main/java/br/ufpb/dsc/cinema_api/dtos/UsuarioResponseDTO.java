package br.ufpb.dsc.cinema_api.dtos;

import br.ufpb.dsc.cinema_api.models.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioResponseDTO {
    private Long usuarioID;
    private String nome;
    private String nomeUsuario;
    private String email;
    private Role role;

}
