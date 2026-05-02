package org.example.modelo;

import java.time.LocalDate;
import java.util.Objects;

public class Examen implements Comparable<Examen>{
    // Atributos de la clase
    private int codigo;
    private LocalDate fecha;
    private float nota;

    // Relaciones
    private Alumno alumno;
    private Asignatura asignatura;

    public Examen(int codigo, Alumno alumno, Asignatura asignatura, LocalDate fecha, float nota) {
        this.codigo=codigo;
        this.alumno = alumno;
        this.asignatura = asignatura;
        this.fecha = fecha;
        this.nota = nota;
        alumno.getExamenes().add(this);
        asignatura.getExamenes().add(this);
    }

    public Examen() {

    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    @Override
    public String toString() {
        return codigo + " - " + fecha + " - " + asignatura.getNombre() + ": " + nota;
    }

    // Implementamos este método para la ordenación por fechas
    @Override
    public int compareTo(Examen o) {
        return this.getFecha().compareTo(o.getFecha());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Examen examen = (Examen) o;
        return codigo == examen.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigo);
    }
}