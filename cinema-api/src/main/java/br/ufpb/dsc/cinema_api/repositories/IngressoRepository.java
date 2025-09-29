package br.ufpb.dsc.cinema_api.repositories;

import br.ufpb.dsc.cinema_api.models.Ingresso;
import br.ufpb.dsc.cinema_api.models.enums.StatusIngresso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngressoRepository extends JpaRepository<Ingresso, Long> {
    Optional<Ingresso> findFirstBySessao_SessaoIDAndStatus(Long sessaoID, StatusIngresso status);
    List<Ingresso> findBySessaoSessaoIDAndStatus(Long sessaoID, StatusIngresso status);
}
