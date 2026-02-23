package dev.ycosorio.literalura.service;

import dev.ycosorio.literalura.model.Autor;
import dev.ycosorio.literalura.model.Libro;
import dev.ycosorio.literalura.model.RespuestaLibro;
import dev.ycosorio.literalura.repository.AutorRepository;
import dev.ycosorio.literalura.repository.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioLibro {

    private final LibrosRepository librosRepositorio;
    private final AutorRepository autorRepository;

    @Autowired
    public ServicioLibro(LibrosRepository librosRepositorio, AutorRepository autorRepository) {
        this.librosRepositorio = librosRepositorio;
        this.autorRepository = autorRepository;
    }

    public void guardarLibro(RespuestaLibro l) {
        Libro libro = new Libro(l);
        List<Autor> autoresDB = l.autores().stream()
                .map(a -> autorRepository.findByNombre(a.nombre())
                        .orElseGet(() -> autorRepository.save(new Autor(a.nombre(), a.nacimiento(), a.muerte()))))
                .collect(Collectors.toList());
        autoresDB.forEach(a -> a.getLibrosEscritos().add(libro));
        libro.setAutores(autoresDB);

        librosRepositorio.save(libro);
    }

    public List<Libro> listarLibros() {
        return librosRepositorio.findAll();
    }

    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> listarAutoresVivos(Integer anio) {
        return autorRepository.autoresVivosEnDeterminadoAnio(anio);
    }

    public List<String> listarIdiomas() {
        return librosRepositorio.findIdiomasRegistrados();
    }

    public List<Libro> listarLibrosPorIdioma(String idioma) {
        return librosRepositorio.findByIdiomas(idioma);
    }

    public List<Libro> buscarLibrosPorAutor(String autor) {
        return librosRepositorio.findByAutoresNombreContainingIgnoreCase(autor);
    }

    public List<Libro> listarTop10() {
        return librosRepositorio.findTop10ByOrderByNumeroDescargasDesc();
    }

    public DoubleSummaryStatistics generarEstadisticas() {
        return librosRepositorio.findAll().stream()
                .filter(l -> l.getNumeroDescargas() != null)
                .mapToDouble(Libro::getNumeroDescargas)
                .summaryStatistics();
    }
}