package br.ufpb.dsc.cinema_api.service;

import br.ufpb.dsc.cinema_api.exception.UsuarioNotFoundException;
import br.ufpb.dsc.cinema_api.models.Usuario;
import br.ufpb.dsc.cinema_api.models.enums.Role;
import br.ufpb.dsc.cinema_api.repositories.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario listarUsuario(Long usuarioID) {
        return usuarioRepository
                .findById(usuarioID)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado!"));
    }

    public void deletarUsuario(Long usuarioID) {
        Usuario usuario = usuarioRepository
                .findById(usuarioID)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado!"));
        usuarioRepository.delete(usuario);
    }

    public Usuario criarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarUsuario(Long usuarioID, Usuario usuario) {
        Usuario u = usuarioRepository.findById(usuarioID)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado!"));

        Usuario usuarioAtualizado = new Usuario();
        usuarioAtualizado.setNome(u.getNome());
        usuarioAtualizado.setNomeUsuario(u.getNomeUsuario());
        usuarioAtualizado.setEmail(u.getEmail());
        usuarioAtualizado.setSenha(bCryptPasswordEncoder.encode(u.getSenha()));

        return usuarioRepository.save(usuarioAtualizado);


    }


}
