package br.ufpb.dsc.cinema_api.controllers;

import br.ufpb.dsc.cinema_api.dtos.FilmeDTO;
import br.ufpb.dsc.cinema_api.dtos.UsuarioDTO;
import br.ufpb.dsc.cinema_api.dtos.UsuarioResponseDTO;
import br.ufpb.dsc.cinema_api.models.Filme;
import br.ufpb.dsc.cinema_api.models.Usuario;
import br.ufpb.dsc.cinema_api.service.FilmeService;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/cinema")
@Validated
public class FilmeController {

    private FilmeService filmeService;
    private ModelMapper modelMapper;

    public FilmeController(FilmeService filmeService, ModelMapper modelMapper) {
        this.filmeService = filmeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(path = "/filmes")
    public List<FilmeDTO> listaTodosFilmes() {
        return filmeService.listarTodosFilmes()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private FilmeDTO convertToDTO(Filme filme) {
        return modelMapper.map(filme, FilmeDTO.class);
    }

    private Filme convertToEntity(FilmeDTO filmeDTO) {
        return modelMapper.map(filmeDTO, Filme.class);
    }

}
