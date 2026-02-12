package dev.ycosorio.literalura.service;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RespuestaApi(

        @JsonAlias("count") Integer cantidad,
        @JsonAlias("next") String pagSiguiente,
        @JsonAlias("previous") String pagAnterior,
        @JsonAlias("results") List<RespuestaLibro> listaDeLibros
) {}

@JsonIgnoreProperties(ignoreUnknown = true)
record RespuestaLibro(
        Integer id,
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<RespuestaAutor> autores,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("bookshelves") List<String> categorias,
        @JsonAlias("download_count") Integer numeroDescargas
        ){}

@JsonIgnoreProperties(ignoreUnknown = true)
record RespuestaAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer nacimiento,
        @JsonAlias("death_year") Integer muerte
){}
