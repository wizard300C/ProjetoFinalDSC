package br.ufpb.dsc.cinema_api.service;

import br.ufpb.dsc.cinema_api.exception.FilmeNotFoundException;
import br.ufpb.dsc.cinema_api.models.Filme;
import br.ufpb.dsc.cinema_api.models.enums.ClassificacaoIndicativa;
import br.ufpb.dsc.cinema_api.models.enums.Genero;
import br.ufpb.dsc.cinema_api.repositories.FilmeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmeService {
    private FilmeRepository filmeRepository;

    public FilmeService(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    public List<Filme> listarFilmesGenero(Genero genero) {
        return filmeRepository.findByGenero(genero);
    }

    public List<Filme> listarFilmesClassificacaoIndicativa(ClassificacaoIndicativa classificacaoIndicativa) {
        return filmeRepository.findByClassificacaoIndicativa(classificacaoIndicativa);
    }

    public Filme adicionarFilme(Filme filme) {
        return filmeRepository.save(filme);
    }

    public void deletarFilme(Long filmeID) {
        Filme filme = filmeRepository.findById(filmeID)
                .orElseThrow(() -> new FilmeNotFoundException("Filme" + filmeID + "não encontrado!"));
        filmeRepository.delete(filme);
    }

    public List<Filme> listarTodosFilmes() {
        return filmeRepository.findAll();
    }

    public Filme listarFilme(Long filmeID) {
        return filmeRepository
                .findById(filmeID)
                .orElseThrow(() -> new FilmeNotFoundException("Filme" + filmeID + "não encontrado"));
    }

    public Filme buscarFilmePorNome(String nomeFilme) {
        return filmeRepository.findByNomeFilme(nomeFilme)
                .orElseThrow(() -> new FilmeNotFoundException("Filme com o nome" + nomeFilme + "não encontrado."));
    }

    public Filme atualizarFilme(Long filmeID, Filme filme) {
        Filme f = filmeRepository.findById(filmeID)
                .orElseThrow(() -> new FilmeNotFoundException("Filme" + filmeID + "não encontrado"));

        f.setNomeFilme(filme.getNomeFilme());
        f.setSinopse(filme.getSinopse());
        f.setGenero(filme.getGenero());
        f.setDuracao(filme.getDuracao());
        f.setClassificacaoIndicativa(filme.getClassificacaoIndicativa());

        return filmeRepository.save(f);
    }

}
