package dev.ycosorio.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private Integer nacimiento;
    private Integer muerte;
    @ManyToMany
    private List<Libro> libros;
    public Autor(){}

    public Autor (RespuestaAutor respuestaAutor){
        this.nombre = respuestaAutor.nombre();
        this.nacimiento = respuestaAutor.nacimiento();
        this.muerte = respuestaAutor.muerte();
    }
    public Autor(String nombre, Integer nacimiento, Integer muerte) {
        this.nombre = nombre;
        this.nacimiento = nacimiento;
        this.muerte = muerte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Integer nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Integer getMuerte() {
        return muerte;
    }

    public void setMuerte(Integer muerte) {
        this.muerte = muerte;
    }

    public List<Libro> getLibrosEscritos() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + '\'' +
                " (" + nacimiento +
                "-" + muerte +
                ")";
    }
}
