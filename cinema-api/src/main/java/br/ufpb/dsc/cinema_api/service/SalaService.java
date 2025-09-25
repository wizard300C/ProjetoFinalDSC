package br.ufpb.dsc.cinema_api.service;

import br.ufpb.dsc.cinema_api.exception.SalaNotFoundException;
import br.ufpb.dsc.cinema_api.models.Sala;
import br.ufpb.dsc.cinema_api.repositories.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {
    private SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public Sala adicionarSala(Sala sala) {
        return salaRepository.save(sala);
    }

    public void removerSala(Long salaID) {
        Sala sala = salaRepository.findById(salaID)
                .orElseThrow(() -> new SalaNotFoundException("A sala " + salaID + " não foi encontrada!"));
        salaRepository.delete(sala);
    }

    public Sala atualizarSala(Long salaID, Sala sala) {
        Sala s = salaRepository.findById(salaID)
                .orElseThrow(() -> new SalaNotFoundException("A sala " + salaID + "não foi encontrada!"));

        if (sala.getNomeSala() != null && !sala.getNomeSala().isBlank()) {
            s.setNomeSala(sala.getNomeSala());
        }

        if (sala.getCapacidade() != null && sala.getCapacidade() > 0) {
            s.setCapacidade(sala.getCapacidade());
        }
        return salaRepository.save(s);
    }

    public List<Sala> listarTodasSalas() {
        return salaRepository.findAll();
    }

    public Sala listarSala(Long salaID) {
        return salaRepository.findById(salaID)
                .orElseThrow(() -> new SalaNotFoundException("A sala" + salaID + "não foi encontrada!"));
    }


}
