package br.ufpb.dsc.cinema_api.controllers;

import br.ufpb.dsc.cinema_api.dtos.FilmeDTO;
import br.ufpb.dsc.cinema_api.models.Filme;
import br.ufpb.dsc.cinema_api.models.enums.ClassificacaoIndicativa;
import br.ufpb.dsc.cinema_api.models.enums.Genero;
import br.ufpb.dsc.cinema_api.service.FilmeService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("isAuthenticated()")
    public List<FilmeDTO> listaTodosFilmes() {
        return filmeService.listarTodosFilmes()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @DeleteMapping(path = "/filmes/{filmeID}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deletaFilme(@PathVariable Long filmeID) {
        filmeService.deletarFilme(filmeID);
    }

    @PostMapping(path = "/filmes")
    @PreAuthorize("hasRole('ADMIN')")
    public FilmeDTO adicionaFilme(@Valid @RequestBody FilmeDTO filmeDTO) {
        Filme filme = convertToEntity(filmeDTO);
        Filme filmeCriado = filmeService.adicionarFilme(filme);
        return convertToDTO(filmeCriado);
    }

    @GetMapping(path = "/filmes/genero")
    @PreAuthorize("isAuthenticated()")
    public List<FilmeDTO> listaFilmesGenero(@Valid @RequestParam("genero") Genero genero) {
        return filmeService.listarFilmesGenero(genero)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/filmes/nome")
    @PreAuthorize("isAuthenticated()")
    public FilmeDTO listaFilmeNome(@Valid @RequestParam("nome") String nomeFilme) {
        Filme filme = filmeService.buscarFilmePorNome(nomeFilme);
        return convertToDTO(filme);
    }

    @PutMapping(path = "/filmes/{filmeID}")
    @PreAuthorize("hasRole('ADMIN')")
    public FilmeDTO atualizaFilme(@PathVariable Long filmeID, @Valid @RequestBody FilmeDTO filmeDTO) {
        Filme filme = convertToEntity(filmeDTO);
        Filme filmeAtualizado = filmeService.atualizarFilme(filmeID, filme);
        return convertToDTO(filmeAtualizado);
    }

    @GetMapping(path = "/filmes/{filmeID}")
    @PreAuthorize("isAuthenticated()")
    public FilmeDTO listaFilme(@PathVariable Long filmeID) {
        Filme filme = filmeService.listarFilme(filmeID);
        return convertToDTO(filme);
    }

    @GetMapping(path = "/filmes/classificacao")
    @PreAuthorize("isAuthenticated()")
    public List<FilmeDTO> listaFilmesClassificacao(@Valid @RequestParam("classificacao")
                                                   ClassificacaoIndicativa classificacaoIndicativa) {
        return filmeService.listarFilmesClassificacaoIndicativa(classificacaoIndicativa)
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
