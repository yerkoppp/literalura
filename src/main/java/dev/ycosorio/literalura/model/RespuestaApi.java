package dev.ycosorio.literalura.model;

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

