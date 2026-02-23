package dev.ycosorio.literalura;

import dev.ycosorio.literalura.principal.Principal;
import dev.ycosorio.literalura.repository.AutorRepository;
import dev.ycosorio.literalura.repository.LibrosRepository;
import dev.ycosorio.literalura.service.ServicioLibro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private ServicioLibro servicioLibro;
	public LiteraluraApplication() {
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal(servicioLibro);
		principal.mostrarMenu();
	}
}
