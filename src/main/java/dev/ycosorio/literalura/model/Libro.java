package dev.ycosorio.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<Autor> autores;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> idiomas;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> categorias;
    private Integer numeroDescargas;

    public Libro(){}

    public Libro(RespuestaLibro respuestaLibro) {
        this.titulo = respuestaLibro.titulo();
        this.idiomas = respuestaLibro.idiomas();
        this.categorias = respuestaLibro.categorias();
        this.numeroDescargas = respuestaLibro.numeroDescargas();
    }
    public Libro(Long id, String titulo, List<Autor> autores, List<String> idiomas, List<String> categorias, Integer numeroDescargas) {
        this.id = id;
        this.titulo = titulo;
        this.autores = autores;
        this.idiomas = idiomas;
        this.categorias = categorias;
        this.numeroDescargas = numeroDescargas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
                ", Autores: " + autores.toString() +
                ", Descargas: " + numeroDescargas +
                ", Idiomas: " + idiomas +
                '.';
    }
}
