package br.ufpb.dsc.cinema_api.controllers;

import br.ufpb.dsc.cinema_api.dtos.AlterarDadosDTO;
import br.ufpb.dsc.cinema_api.dtos.AlterarSenhaDTO;
import br.ufpb.dsc.cinema_api.dtos.RegistroDTO;
import br.ufpb.dsc.cinema_api.dtos.UsuarioResponseDTO;
import br.ufpb.dsc.cinema_api.exception.RegistroException;
import br.ufpb.dsc.cinema_api.models.Usuario;
import br.ufpb.dsc.cinema_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/auth")
@Validated
public class AuthController {
    private  UsuarioService usuarioService;
    private  ModelMapper modelMapper;

    public AuthController(UsuarioService usuarioService, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.usuarioService = usuarioService;
    }

    @PostMapping(path = "/registro")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDTO registraUsuario(@Valid @RequestBody RegistroDTO registroDTO) {

        Usuario usuario = convertToEntity(registroDTO);

        if (usuarioService.existePorNomeUsuario(usuario.getNomeUsuario())) {
            throw new RegistroException("Nome de usuário já existe!");
        }

        if (usuarioService.existePorEmail(usuario.getEmail())) {
            throw new RegistroException("O email já está em uso!");
        }

        Usuario usuarioSalvo = usuarioService.registrarCliente(usuario);
        return convertToResponseDTO(usuarioSalvo);
    }

    @PostMapping(path = "/alterar-senha")
    @ResponseStatus(HttpStatus.OK)
    public String alteraSenha(@Valid @RequestBody AlterarSenhaDTO dto, Authentication authentication) {
        String nomeUsuario = authentication.getName();
        usuarioService.alterarSenha(nomeUsuario, dto);
        return "Senha alterada com sucesso!";
    }

    @PutMapping("/alterar-dados")
    @ResponseStatus(HttpStatus.OK)
    public UsuarioResponseDTO atualizaDados(@Valid @RequestBody AlterarDadosDTO dto, Authentication authentication) {
        String nomeUsuarioLogado = authentication.getName();
        Usuario dadosParaAtualizar = modelMapper.map(dto, Usuario.class);
        Usuario usuarioAtualizado = usuarioService.atualizarDados(nomeUsuarioLogado, dadosParaAtualizar);
        return convertToResponseDTO(usuarioAtualizado);
    }

    @GetMapping("/listar-dados")
    public UsuarioResponseDTO listaDados(Authentication authentication) {
        String nomeUsuarioLogado = authentication.getName();
        Usuario usuario = usuarioService.buscarPorNomeUsuario(nomeUsuarioLogado);
        return convertToResponseDTO(usuario);
    }

    private UsuarioResponseDTO convertToResponseDTO(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }

    private Usuario convertToEntity(RegistroDTO userDTO) {
        return modelMapper.map(userDTO, Usuario.class);
    }
}
