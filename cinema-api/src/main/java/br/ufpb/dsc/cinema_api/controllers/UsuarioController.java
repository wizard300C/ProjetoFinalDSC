package br.ufpb.dsc.cinema_api.controllers;

import br.ufpb.dsc.cinema_api.dtos.UsuarioDTO;
import br.ufpb.dsc.cinema_api.dtos.UsuarioResponseDTO;
import br.ufpb.dsc.cinema_api.models.Usuario;
import br.ufpb.dsc.cinema_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/cinema")
@Validated
public class
UsuarioController {

    private UsuarioService usuarioService;
    private ModelMapper modelMapper;

    public UsuarioController(UsuarioService usuarioService, ModelMapper modelMapper) {
        this.usuarioService = usuarioService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(path = "/usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDTO criaUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = convertToEntity(usuarioDTO);
        Usuario usuarioCriado = usuarioService.criarUsuario(usuario);
        return convertToResponseDTO(usuarioCriado);
    }

    @PutMapping(path = "/usuarios/{usuarioID}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public UsuarioResponseDTO atualizaUsuario(@PathVariable Long usuarioID, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = convertToEntity(usuarioDTO);
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(usuarioID, usuario);
        return convertToResponseDTO(usuarioAtualizado);
    }

    @DeleteMapping(path = "/usuarios/{usuarioID}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deletaUsuario(@PathVariable Long usuarioID) {
        usuarioService.deletarUsuario(usuarioID);
    }

    @GetMapping(path = "/usuarios")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UsuarioResponseDTO> listaTodosUsuarios() {
        return usuarioService
                .listarTodosUsuarios()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/usuarios/{usuarioID}")
    @PreAuthorize("hasRole('ADMIN')")
    public UsuarioResponseDTO listaUsuario(@PathVariable Long usuarioID) {
        Usuario usuario = usuarioService.listarUsuario(usuarioID);
        return convertToResponseDTO(usuario);
    }

    private UsuarioResponseDTO convertToResponseDTO(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }

    private Usuario convertToEntity(UsuarioDTO usuarioDTO) {
        return modelMapper.map(usuarioDTO, Usuario.class);
    }


}
