package br.ufpb.dsc.cinema_api.controllers;

import br.ufpb.dsc.cinema_api.dtos.FilmeDTO;
import br.ufpb.dsc.cinema_api.dtos.IngressoDTO;
import br.ufpb.dsc.cinema_api.models.Filme;
import br.ufpb.dsc.cinema_api.models.Ingresso;
import br.ufpb.dsc.cinema_api.models.Usuario;
import br.ufpb.dsc.cinema_api.service.IngressoService;
import br.ufpb.dsc.cinema_api.service.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(path = "/api/cinema")
@RestController
@Validated
public class IngressoController {
    private IngressoService ingressoService;
    private ModelMapper modelMapper;
    private UsuarioService usuarioService;

    public IngressoController(ModelMapper modelMapper, IngressoService ingressoService, UsuarioService usuarioService) {
        this.ingressoService = ingressoService;
        this.modelMapper = modelMapper;
        this.usuarioService = usuarioService;
    }

    @PostMapping(path = "/sessoes/{sessaoID}/comprar")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public IngressoDTO compraIngresso(@PathVariable Long sessaoID, Authentication authentication) {
        String nomeUsuario = authentication.getName();

        Usuario usuario = usuarioService.buscarPorNomeUsuario(nomeUsuario);

        Ingresso ingresso = ingressoService.comprarIngresso(sessaoID, usuario.getUsuarioID());

        return convertToDTO(ingresso);
    }

    @GetMapping(path = "/sessoes/{sessaoID}/ingressos")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    public List<IngressoDTO> listaTodosIngressosDisponiveis(@PathVariable Long sessaoID) {
        return ingressoService.ListarTodosIngressosDisponiveis(sessaoID)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private IngressoDTO convertToDTO(Ingresso ingresso) {
        return modelMapper.map(ingresso, IngressoDTO.class);
    }

    private Ingresso convertToEntity(IngressoDTO ingressoDTO) {
        return modelMapper.map(ingressoDTO, Ingresso.class);
    }


}
