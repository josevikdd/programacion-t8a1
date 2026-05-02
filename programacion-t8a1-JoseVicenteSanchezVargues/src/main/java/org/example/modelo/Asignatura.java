package org.example.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Asignatura implements Mostrable{

    // Atributos de la clase
    private int codigo;
    private String nombre;

    //  Relaciones
    private Profesor profesor; // puede ser null ya que no tiene asignado todavia profesor
    private Curso curso;

    // Se hace final para que no se pueda cambiar ni reasignar de nuevo
    private final List<Alumno> alumnos = new ArrayList<>();
    private final List<Examen> examenes = new ArrayList<>();

    public Asignatura(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Asignatura(){
        super();
    }

    public int getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public Profesor getProfesor() { return profesor; }

    public void setCodigo(int codigo){
        this.codigo=codigo;
    }

    public void setCurso(Curso curso){
        this.curso = curso;
    }

    public Curso getCurso(){
        return curso;
    }

    public List<Alumno> getAlumnos() { return alumnos; }
    public List<Examen> getExamenes() { return examenes; }

    public void impartir(Profesor profesor){
        if(profesor.getAsignaturas().size()>2){
            System.out.println("Error. El profesor tiene ya 2 o más asignaturas asignadas.");
        }else{
            // Creamos la relación entre profesor y asignatura
            profesor.getAsignaturas().add(this);
            this.profesor = profesor;
        }
    }

    @Override
    public void mostrarDatos() {
        if(this.profesor!=null) {
            System.out.println("Código: " + codigo + " - Nombre: " + nombre + " - Profesor: " +
                    this.profesor.getCodigo() + " - " + this.profesor.getNombre() + " " + this.profesor.getApellidos());
        }else{
            System.out.println("Código: " + codigo + " - Nombre: " + nombre + " - Sin profesor.");
        }
    }

    public void dejarDeImpartir(Profesor profesor) {
        profesor.getAsignaturas().remove(this);
        this.profesor=null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Asignatura that = (Asignatura) o;
        return codigo == that.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }
}