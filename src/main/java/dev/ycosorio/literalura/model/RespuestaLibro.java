package dev.ycosorio.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.ycosorio.literalura.model.RespuestaAutor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RespuestaLibro(
        Long id,
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<RespuestaAutor> autores,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("bookshelves") List<String> categorias,
        @JsonAlias("download_count") Integer numeroDescargas
) {
}
