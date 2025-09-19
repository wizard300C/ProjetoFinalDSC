package br.ufpb.dsc.cinema_api.service;

import br.ufpb.dsc.cinema_api.dtos.AlterarSenhaDTO;
import br.ufpb.dsc.cinema_api.exception.RegistroException;
import br.ufpb.dsc.cinema_api.exception.TrocarSenhaException;
import br.ufpb.dsc.cinema_api.exception.UsuarioNotFoundException;
import br.ufpb.dsc.cinema_api.models.Usuario;
import br.ufpb.dsc.cinema_api.models.enums.Role;
import br.ufpb.dsc.cinema_api.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário" + usuarioID + "não encontrado!"));
    }

    public boolean existePorNomeUsuario(String nomeUsuario) {
        return usuarioRepository.existsByNomeUsuario(nomeUsuario);
    }

    public boolean existePorEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public void deletarUsuario(Long usuarioID) {
        Usuario usuario = usuarioRepository
                .findById(usuarioID)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário" + usuarioID + "não encontrado!"));
        usuarioRepository.delete(usuario);
    }

    public Usuario criarUsuario(Usuario usuario) {
        usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Usuario registrarCliente(Usuario usuario) {
        usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
        usuario.setRole(Role.CLIENTE);
        usuarioRepository.save(usuario);
        return usuario;
    }

    public Usuario buscarPorNomeUsuario(String nomeUsuario) {
        Usuario usuario = usuarioRepository.findByNomeUsuario(nomeUsuario);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário" + nomeUsuario + "não encontrado!");
        }
        return usuario;
    }

    public Usuario atualizarUsuario(Long usuarioID, Usuario usuario) {
        Usuario u = usuarioRepository.findById(usuarioID)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário" + usuarioID + "não encontrado!"));

        if (usuario.getNome() != null && !usuario.getNome().isBlank()) {
            u.setNome(usuario.getNome());
        }

        if (usuario.getNomeUsuario() != null && !usuario.getNomeUsuario().isBlank()) {
            u.setNomeUsuario(usuario.getNomeUsuario());
        }
        if (usuario.getEmail() != null && !usuario.getEmail().isBlank()) {
            u.setEmail(usuario.getEmail());
        }

        return usuarioRepository.save(u);
    }

    public void alterarSenha(String nomeUsuarioLogado, AlterarSenhaDTO dto) {
        Usuario usuario = usuarioRepository.findByNomeUsuario(nomeUsuarioLogado);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário" + nomeUsuarioLogado + "não encontrado!");
        }

        if (!bCryptPasswordEncoder.matches(dto.getSenhaAtual(), usuario.getSenha())) {
            throw new TrocarSenhaException("Senha atual incorreta.");
        }

        if (!dto.getNovaSenha().equals(dto.getConfirmacaoNovaSenha())) {
            throw new TrocarSenhaException("A nova senha e a confirmação não coincidem.");
        }

        String novaSenhaCriptografada = bCryptPasswordEncoder.encode(dto.getNovaSenha());
        usuario.setSenha(novaSenhaCriptografada);
        usuarioRepository.save(usuario);
    }

    public Usuario atualizarDados(String nomeUsuarioLogado, Usuario usuario) {
        Usuario usuarioAtual = usuarioRepository.findByNomeUsuario(nomeUsuarioLogado);
        if (usuarioAtual == null) {
            throw new UsernameNotFoundException(" Utilizador não encontrado!");
        }

        if (usuario.getNome() != null && !usuario.getNome().isBlank()) {
            usuarioAtual.setNome(usuario.getNome());
        }

        if (usuario.getNomeUsuario() != null && !usuario.getNomeUsuario().isBlank()) {
            if (usuarioRepository.existsByNomeUsuario(usuario.getNomeUsuario()) && !usuarioAtual.getNomeUsuario().equals(usuario.getNomeUsuario())) {
                throw new RegistroException("Nome de usuário já está em uso por outra conta.");
            }
            usuarioAtual.setNomeUsuario(usuario.getNomeUsuario());
        }

        if (usuario.getEmail() != null && !usuario.getEmail().isBlank()) {
            if (usuarioRepository.existsByEmail(usuario.getEmail()) && !usuarioAtual.getEmail().equals(usuario.getEmail())) {
                throw new RegistroException("Email já está em uso por outra conta.");
            }
            usuarioAtual.setEmail(usuario.getEmail());
        }

        return usuarioRepository.save(usuarioAtual);
    }
}
