package br.ufpb.dsc.cinema_api.repositories;

import br.ufpb.dsc.cinema_api.models.Usuario;
import br.ufpb.dsc.cinema_api.models.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

   boolean existsByNomeUsuario(String nomeUsuario);

   boolean existsByEmail(String email);

   boolean existsByRole(Role role);

   Usuario findByNomeUsuario(String nomeUsuario);
}
