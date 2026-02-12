package dev.ycosorio.literalura.model;

import java.util.List;

public class Libro {
    private Integer id;
    private String titulo;
    private List<Autor> autores;
    private List<String> idiomas;
    private List<String> categorias;
    private Integer numeroDescargas;

    Libro(){}

    public Libro(Integer id, String titulo, List<Autor> autores, List<String> idiomas, List<String> categorias) {
        this.id = id;
        this.titulo = titulo;
        this.autores = autores;
        this.idiomas = idiomas;
        this.categorias = categorias;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }

    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Integer numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "Título: '" + titulo + '\'' +
                ", Autores: " + autores +
                ", Descargas: " + numeroDescargas +
                ", Idiomas: " + idiomas +
                ", Categorías: " + categorias +
                '}';
    }
}
