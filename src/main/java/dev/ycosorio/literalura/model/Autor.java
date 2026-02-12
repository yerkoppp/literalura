package dev.ycosorio.literalura.model;

import java.util.List;

public class Autor {

    private String nombre;
    private Integer nacimiento;
    private Integer muerte;
    private List<Libro> librosEscritos;
    Autor(){}
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
        return librosEscritos;
    }

    public void setLibrosEscritos(List<Libro> librosEscritos) {
        this.librosEscritos = librosEscritos;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + '\'' +
                " (" + nacimiento +
                "-" + muerte +
                ")";
    }
}
