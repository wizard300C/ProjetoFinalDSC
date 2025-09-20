package br.ufpb.dsc.cinema_api.service;

import br.ufpb.dsc.cinema_api.exception.FilmeNotFoundException;
import br.ufpb.dsc.cinema_api.exception.SalaNotFoundException;
import br.ufpb.dsc.cinema_api.exception.SessaoNotFoundException;
import br.ufpb.dsc.cinema_api.models.Filme;
import br.ufpb.dsc.cinema_api.models.Sala;
import br.ufpb.dsc.cinema_api.models.Sessao;
import br.ufpb.dsc.cinema_api.repositories.FilmeRepository;
import br.ufpb.dsc.cinema_api.repositories.SalaRepository;
import br.ufpb.dsc.cinema_api.repositories.SessaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessaoService {

    private SessaoRepository sessaoRepository;
    private FilmeRepository filmeRepository;
    private SalaRepository salaRepository;

    public SessaoService(SessaoRepository sessaoRepository,
                         SalaRepository salaRepository,
                         FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
        this.salaRepository = salaRepository;
        this.sessaoRepository = sessaoRepository;
    }

    public Sessao criarSessao(Long filmeID, Sessao sessao) {
        Filme filme = filmeRepository.findById(filmeID)
                .orElseThrow(() -> new FilmeNotFoundException("O filme " + filmeID + " não foi encontrado"));
        Sala sala = salaRepository.findById(sessao.getSala().getSalaID())
                .orElseThrow(() -> new SalaNotFoundException("Sala não encontrada!"));

        sessao.setFilme(filme);
        sessao.setSala(sala);
        return sessaoRepository.save(sessao);
    }

    public void removerSessao(Long sessaoID) {
        Sessao sessao = sessaoRepository.findById(sessaoID)
                .orElseThrow(() -> new SessaoNotFoundException("Sessão " + sessaoID + " não foi encontrada!"));
        sessaoRepository.delete(sessao);
    }

    public List<Sessao> listarSessoesFilme(Long filmeID) {
        if (!filmeRepository.existsById(filmeID)) {
            throw new FilmeNotFoundException("Filme " + filmeID + " não foi encontrado!");
        }
        return sessaoRepository.findByFilme(filmeID);
    }

    public Sessao listarSessao(Long sessaoID) {
        return sessaoRepository.findById(sessaoID)
                .orElseThrow(() -> new SessaoNotFoundException("Sessão " + sessaoID + " não foi encontrada!"));
    }

    public Sessao atualizarSessao(Long sessaoID, Sessao sessao) {
        Sessao s = sessaoRepository.findById(sessaoID)
                .orElseThrow(() -> new SessaoNotFoundException("Sessão " + sessaoID + " não foi encontrada!"));

        if (sessao.getFilme() != null) {
            Filme filme = filmeRepository.findById(sessao.getFilme().getFilmeID())
                    .orElseThrow(() -> new FilmeNotFoundException("Filme com ID " + sessao.getFilme().getFilmeID() + " não foi encontrado!"));
            s.setFilme(filme);
        }

        if (sessao.getSala() != null) {
            Sala sala = salaRepository.findById(sessao.getSala().getSalaID())
                    .orElseThrow(() -> new SalaNotFoundException("Sala com ID " + sessao.getSala().getSalaID() + " não foi encontrada!"));
            s.setSala(sala);
        }

        if (sessao.getHorario() != null) {
            s.setHorario(sessao.getHorario());
        }

        return sessaoRepository.save(s);
    }

    public List<Sessao> listarTodasSessoes(LocalDate data, Long salaID, Long filmeID){
        List<Sessao> sessaos = sessaoRepository.findAll();

        return sessaos.stream()
                .filter(s -> data == null || s.getHorario().toLocalDate().isEqual(data))
                .filter(s -> salaID == null || s.getSala().getSalaID().equals(salaID))
                .filter(s -> filmeID == null || s.getFilme().getFilmeID().equals(filmeID))
                .collect(Collectors.toList());
    }

}

