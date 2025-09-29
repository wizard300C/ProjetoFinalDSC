package br.ufpb.dsc.cinema_api;

import br.ufpb.dsc.cinema_api.models.Usuario;
import br.ufpb.dsc.cinema_api.models.enums.Role;
import br.ufpb.dsc.cinema_api.repositories.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class CinemaApiApplication {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(CinemaApiApplication.class, args);
	}

	@Component
	public class AdminUserInitializer implements CommandLineRunner {

		private final UsuarioRepository usuarioRepository;
		private final PasswordEncoder passwordEncoder;

		public AdminUserInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
			this.usuarioRepository = usuarioRepository;
			this.passwordEncoder = passwordEncoder;
		}

		@Override
		public void run(String... args) throws Exception {
			if (!usuarioRepository.existsByRole(Role.ADMIN)) {
				System.out.println("Nenhum administrador encontrado, a criar utilizador admin padrão...");

				Usuario admin = new Usuario();
				admin.setNome("Administrador Padrão");
				admin.setNomeUsuario("admin");
				admin.setEmail("admin@cinema.com");
				admin.setSenha(passwordEncoder.encode("admin123")); // Use uma senha forte
				admin.setRole(Role.ADMIN);

				usuarioRepository.save(admin);
				System.out.println("Utilizador admin padrão criado com sucesso.");
			}
		}
	}

}
