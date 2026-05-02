package org.example.modelo;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Persona {

    // Atributos de la clase
    private int codigo;
    private String nombre;
    private String apellidos;
    private String poblacion;
    private LocalDate fechaNacimiento;

    // Atributos de la relacion
    private Curso curso;

    public Persona(int codigo, String nombre, String apellidos,
                   String poblacion, LocalDate fechaNacimiento) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.poblacion = poblacion;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Persona(){

    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso){
        this.curso = curso;
    }

    // Implementamos el metodo hashCode y equals que nos servirán para realizas las comparaciones
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Persona persona)) return false;
        return codigo == persona.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }
}
