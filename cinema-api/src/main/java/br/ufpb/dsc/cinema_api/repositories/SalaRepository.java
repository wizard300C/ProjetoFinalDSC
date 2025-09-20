package br.ufpb.dsc.cinema_api.repositories;

import br.ufpb.dsc.cinema_api.models.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {
}
