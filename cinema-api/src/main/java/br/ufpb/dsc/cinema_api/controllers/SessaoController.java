package br.ufpb.dsc.cinema_api.controllers;

import br.ufpb.dsc.cinema_api.dtos.SessaoDTO;
import br.ufpb.dsc.cinema_api.dtos.SessaoResponseDTO;
import br.ufpb.dsc.cinema_api.models.Sessao;
import br.ufpb.dsc.cinema_api.service.SessaoService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/cinema")
@Validated
public class SessaoController {

    private SessaoService sessaoService;
    private ModelMapper modelMapper;

    public SessaoController(SessaoService sessaoService, ModelMapper modelMapper) {
        this.sessaoService = sessaoService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(path = "/filmes/{filmeID}/sessoes")
    @PreAuthorize("isAuthenticated()")
    public List<SessaoResponseDTO> listaSessoesFilme(@PathVariable Long filmeID) {
        return sessaoService.listarSessoesFilme(filmeID)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @PostMapping(path = "/filmes/{filmeID}/sessoes")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public SessaoResponseDTO criaSessao(@PathVariable Long filmeID, @Valid @RequestBody SessaoDTO sessaoDTO) {
        Sessao sessao = convertToEntity(sessaoDTO);
        Sessao sessaoCriada = sessaoService.criarSessao(filmeID, sessao);
        return convertToResponseDTO(sessaoCriada);
    }

    @DeleteMapping(path = "/sessoes/{sessaoID}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void removeSessao(@PathVariable Long sessaoID) {
        sessaoService.removerSessao(sessaoID);
    }

    @PutMapping(path = "/sessoes/{sessaoID}")
    @PreAuthorize("hasRole('ADMIN')")
    public SessaoResponseDTO atualizaSessao(@PathVariable Long sessaoID, @Valid @RequestBody SessaoDTO sessaoDTO) {
        Sessao sessao = convertToEntity(sessaoDTO);
        Sessao sessaoAtualizada = sessaoService.atualizarSessao(sessaoID, sessao);
        return convertToResponseDTO(sessaoAtualizada);
    }

    @GetMapping("/sessoes")
    @PreAuthorize("isAuthenticated()")
    public List<SessaoResponseDTO> listaTodasSessoes(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam(required = false) Long filmeId) {
        return sessaoService.listarTodasSessoes(data, filmeId)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/sessoes/{sessaoID}")
    @PreAuthorize("isAuthenticated()")
    public SessaoResponseDTO listaSessao(@PathVariable Long sessaoID) {
        Sessao sessao = sessaoService.listarSessao(sessaoID);
        return convertToResponseDTO(sessao);
    }

    private SessaoResponseDTO convertToResponseDTO(Sessao sessao){
        return modelMapper.map(sessao, SessaoResponseDTO.class);
    }
    private Sessao convertToEntity(SessaoDTO sessaoDTO) {
        return modelMapper.map(sessaoDTO, Sessao.class);
    }

}
