package br.ufpb.dsc.cinema_api.repositories;

import br.ufpb.dsc.cinema_api.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

   boolean existsByNomeUsuario(String nomeUsuario);

   boolean existsByEmail(String email);

   Usuario findByNomeUsuario(String nomeUsuario);
}
