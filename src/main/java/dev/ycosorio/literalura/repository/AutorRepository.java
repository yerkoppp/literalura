package dev.ycosorio.literalura.repository;

import dev.ycosorio.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    //Método solicitado para Derived Queries, no usado por largo
    List<Autor> findByNacimientoLessThanEqualAndMuerteIsNullOrNacimientoLessThanEqualAndMuerteGreaterThanEqual(Integer anioNacimiento1, Integer anioNacimiento2, Integer anioMuerte);
    @Query("SELECT a FROM Autor a WHERE a.nacimiento <= :anio AND (a.muerte IS NULL OR a.muerte >= :anio)")
    List<Autor> autoresVivosEnDeterminadoAnio(@Param("anio") Integer anio);

    Optional<Autor> findByNombre(String nombre);
    Optional<Autor> findByNombreContainingIgnoreCase(String autorBuscado);


}