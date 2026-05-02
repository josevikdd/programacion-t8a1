package org.example.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Curso {

    // Atributos de la clase
    private int codigo;
    private String descripcion;

    // Relaciones
    // Se hace final para que no se pueda cambiar ni reasignar de nuevo
    private final List<Asignatura> asignaturas = new ArrayList<>();
    private final List<Persona> personas = new ArrayList<>();

    public Curso(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public Curso() {
        super();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo){
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<Persona> getPersonas() {
        return personas;
    }

    public List<Asignatura> getAsignaturas(){
        return asignaturas;
    }

    public void asignarAsignatura(Asignatura asignatura){
        this.asignaturas.add(asignatura);
        asignatura.setCurso(this);
    }

    public void asignarPersona(Persona persona){
        this.getPersonas().add(persona);
        persona.setCurso(this);
    }

    public void desasignarPersona(Persona persona){
        this.getPersonas().remove(persona);
        persona.setCurso(null);
    }

    // Implementamos el metodo hashCode y equals que nos servirán para realizas las comparaciones

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Curso curso = (Curso) o;
        return codigo == curso.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }

    @Override
    public String toString() {
        return this.getCodigo() + " - " + this.getDescripcion();
    }
}