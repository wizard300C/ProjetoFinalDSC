package br.ufpb.dsc.cinema_api.controllers;

import br.ufpb.dsc.cinema_api.dtos.FilmeDTO;
import br.ufpb.dsc.cinema_api.dtos.SessaoDTO;
import br.ufpb.dsc.cinema_api.models.Filme;
import br.ufpb.dsc.cinema_api.models.Sessao;
import br.ufpb.dsc.cinema_api.service.FilmeService;
import br.ufpb.dsc.cinema_api.service.SessaoService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(path = "/filmes/{filmeID}/sessoes")
    @PreAuthorize("hasRole('ADMIN')")
    public SessaoDTO criaSessao(@PathVariable Long filmeID, @Valid @RequestBody SessaoDTO sessaoDTO) {
        Sessao sessao = convertToEntity(sessaoDTO);
        Sessao sessaoCriada = sessaoService.criarSessao(filmeID, sessao);
        return convertToDTO(sessaoCriada);
    }


    private SessaoDTO convertToDTO(Sessao sessao) {
        return modelMapper.map(sessao, SessaoDTO.class);
    }

    private Sessao convertToEntity(SessaoDTO sessaoDTO) {
        return modelMapper.map(sessaoDTO, Sessao.class);
    }

}
