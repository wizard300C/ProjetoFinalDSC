package br.ufpb.dsc.cinema_api.controllers;

import br.ufpb.dsc.cinema_api.dtos.SalaDTO;
import br.ufpb.dsc.cinema_api.models.Sala;
import br.ufpb.dsc.cinema_api.service.SalaService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/cinema")
@Validated
public class SalaController {
    private ModelMapper modelMapper;
    private SalaService salaService;

    public SalaController(SalaService salaService, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.salaService = salaService;
    }

    @PostMapping(path = "/salas")
    @PreAuthorize("hasRole('ADMIN')")
    public SalaDTO adicionaSala(@Valid @RequestBody SalaDTO salaDTO) {
        Sala sala = convertToEntity(salaDTO);
        Sala salaAdicionada = salaService.adicionarSala(sala);
        return convertToDTO(salaAdicionada);
    }

    @DeleteMapping(path = "/salas/{salaID}")
    @PreAuthorize("hasRole('ADMIN')")
    public void removeSala(@PathVariable Long salaID) {
        salaService.removerSala(salaID);
    }

    @GetMapping(path = "/salas")
    @PreAuthorize("hasRole('ADMIN')")
    public List<SalaDTO> listaTodasSalas() {
        return salaService.listarTodasSalas()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/salas/{salaID}")
    @PreAuthorize("hasRole('ADMIN')")
    public SalaDTO listaSala(@PathVariable Long salaID) {
        Sala sala = salaService.listarSala(salaID);
        return convertToDTO(sala);
    }

    @PutMapping(path = "/salas/{salaID}")
    @PreAuthorize("hasRole('ADMIN')")
    public SalaDTO atualizaSala(@PathVariable Long salaID, @Valid @RequestBody SalaDTO salaDTO) {
        Sala sala = convertToEntity(salaDTO);
        Sala salaAtualizada = salaService.atualizarSala(salaID, sala);
        return convertToDTO(salaAtualizada);
    }

    private SalaDTO convertToDTO(Sala sala) {
        return modelMapper.map(sala, SalaDTO.class);
    }

    private Sala convertToEntity(SalaDTO salaDTO) {
        return modelMapper.map(salaDTO, Sala.class);
    }


}
