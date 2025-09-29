package br.ufpb.dsc.cinema_api.service;

import br.ufpb.dsc.cinema_api.exception.SessaoNotFoundException;
import br.ufpb.dsc.cinema_api.exception.UsuarioNotFoundException;
import br.ufpb.dsc.cinema_api.models.Ingresso;
import br.ufpb.dsc.cinema_api.models.Sessao;
import br.ufpb.dsc.cinema_api.models.Usuario;
import br.ufpb.dsc.cinema_api.models.enums.StatusIngresso;
import br.ufpb.dsc.cinema_api.repositories.IngressoRepository;
import br.ufpb.dsc.cinema_api.repositories.SessaoRepository;
import br.ufpb.dsc.cinema_api.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngressoService {
    private UsuarioRepository usuarioRepository;
    private IngressoRepository ingressoRepository;
    private SessaoRepository sessaoRepository;

    public IngressoService(IngressoRepository ingressoRepository,
                           UsuarioRepository usuarioRepository,
                           SessaoRepository sessaoRepository) {
        this.ingressoRepository = ingressoRepository;
        this.sessaoRepository = sessaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Ingresso comprarIngresso(Long sessaoID, Long usuarioID) {
        Sessao sessao = sessaoRepository.findById(sessaoID)
                .orElseThrow(() -> new SessaoNotFoundException("Sessão" + sessaoID + "não foi encontrada!"));

        Usuario usuario = usuarioRepository.findById(usuarioID)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário" + usuarioID + "não foi encontrado!"));

        Ingresso ingressoDisponivel = ingressoRepository
                .findFirstBySessao_SessaoIDAndStatus(sessaoID, StatusIngresso.DISPONIVEL)
                .orElseThrow(() -> new IllegalArgumentException("A sessão está cheia ou não existem ingressos disponíveis."));

        ingressoDisponivel.setUsuario(usuario);
        ingressoDisponivel.setSessao(sessao);
        ingressoDisponivel.setStatus(StatusIngresso.VENDIDO);
        return ingressoRepository.save(ingressoDisponivel);
    }

    public List<Ingresso> ListarTodosIngressosDisponiveis(Long sessaoID) {
        if (!sessaoRepository.existsById(sessaoID)) {
            throw new SessaoNotFoundException("Sessão não encontrada!");
        }
        return ingressoRepository.findBySessaoSessaoIDAndStatus(sessaoID, StatusIngresso.DISPONIVEL);
    }
}
