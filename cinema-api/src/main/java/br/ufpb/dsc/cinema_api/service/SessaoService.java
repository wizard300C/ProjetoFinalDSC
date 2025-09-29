package br.ufpb.dsc.cinema_api.service;

import br.ufpb.dsc.cinema_api.exception.FilmeNotFoundException;
import br.ufpb.dsc.cinema_api.exception.SalaNotFoundException;
import br.ufpb.dsc.cinema_api.exception.SessaoNotFoundException;
import br.ufpb.dsc.cinema_api.models.Filme;
import br.ufpb.dsc.cinema_api.models.Ingresso;
import br.ufpb.dsc.cinema_api.models.Sala;
import br.ufpb.dsc.cinema_api.models.Sessao;
import br.ufpb.dsc.cinema_api.models.enums.StatusIngresso;
import br.ufpb.dsc.cinema_api.repositories.FilmeRepository;
import br.ufpb.dsc.cinema_api.repositories.IngressoRepository;
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
    private IngressoRepository ingressoRepository;

    public SessaoService(SessaoRepository sessaoRepository,
                         SalaRepository salaRepository,
                         FilmeRepository filmeRepository,
                         IngressoRepository ingressoRepository) {
        this.filmeRepository = filmeRepository;
        this.salaRepository = salaRepository;
        this.sessaoRepository = sessaoRepository;
        this.ingressoRepository = ingressoRepository;
    }

    public Sessao criarSessao(Long filmeID, Sessao sessao) {
        Filme filme = filmeRepository.findById(filmeID)
                .orElseThrow(() -> new FilmeNotFoundException("O filme " + filmeID + " não foi encontrado"));
        Sala sala = salaRepository.findById(sessao.getSala().getSalaID())
                .orElseThrow(() -> new SalaNotFoundException("Sala não encontrada!"));

        sessao.setFilme(filme);
        sessao.setSala(sala);

        Sessao sessaoSalva = sessaoRepository.save(sessao);

        for (int i = 1; i <= sala.getCapacidade(); i++) {
            Ingresso novoIngresso = new Ingresso();
            novoIngresso.setSessao(sessaoSalva);
            novoIngresso.setPreco(sessaoSalva.getPreco()); // O preço vem da sessão


            novoIngresso.setAssento("A" + i);

            novoIngresso.setStatus(StatusIngresso.DISPONIVEL); // O status inicial é sempre DISPONIVEL
            novoIngresso.setUsuario(null); // Nenhum utilizador associado ainda

            ingressoRepository.save(novoIngresso);
        }

        return sessaoSalva;

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
        return sessaoRepository.findByFilme_FilmeID(filmeID);
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

        if (sessao.getPreco() != null) {
            s.setPreco(sessao.getPreco());
        }

        if (sessao.getHorario() != null) {
            s.setHorario(sessao.getHorario());
        }

        return sessaoRepository.save(s);
    }

    public List<Sessao> listarTodasSessoes(LocalDate data, Long filmeID) {
        List<Sessao> sessaos = sessaoRepository.findAll();

        return sessaos.stream()
                .filter(s -> data == null || s.getHorario().toLocalDate().isEqual(data))
                .filter(s -> filmeID == null || s.getFilme().getFilmeID().equals(filmeID))
                .collect(Collectors.toList());
    }
}

