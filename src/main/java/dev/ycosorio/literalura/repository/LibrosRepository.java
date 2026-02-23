package dev.ycosorio.literalura.repository;

import dev.ycosorio.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibrosRepository extends JpaRepository <Libro, Long> {

    @Query("SELECT DISTINCT i FROM Libro l JOIN l.idiomas i")
    List<String> findIdiomasRegistrados();

    List<Libro> findByIdiomas(String idioma);

    List<Libro> findTop10ByOrderByNumeroDescargasDesc();

    List<Libro> findByAutoresNombreContainingIgnoreCase(String nombreAutor);
}