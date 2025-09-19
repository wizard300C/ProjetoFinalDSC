package br.ufpb.dsc.cinema_api.repositories;

import br.ufpb.dsc.cinema_api.models.Filme;
import br.ufpb.dsc.cinema_api.models.enums.ClassificacaoIndicativa;
import br.ufpb.dsc.cinema_api.models.enums.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
    List<Filme> findByGenero(Genero genero);
    List<Filme> findByClassificacaoIndicativa(ClassificacaoIndicativa classificacaoIndicativa);
}
