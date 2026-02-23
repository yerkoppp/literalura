package dev.ycosorio.literalura.repository;

import dev.ycosorio.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a WHERE a.nacimiento <= :anio AND (a.muerte IS NULL OR a.muerte >= :anio)")
    List<Autor> autoresVivosEnDeterminadoAnio(@Param("anio") Integer anio);
}