package org.example.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Alumno extends Persona implements Mostrable{

    // Relaciones
    // Se hace final para que no se pueda cambiar ni reasignar de nuevo
    private final List<Examen> examenes = new ArrayList<>();

    public Alumno(int codigo, String nombre, String apellidos,
                  String poblacion, LocalDate fechaNacimiento) {
        super(codigo, nombre, apellidos, poblacion, fechaNacimiento);
    }

    public Alumno() {

    }

    public List<Examen> getExamenes() {
        return examenes;
    }

    @Override
    public void mostrarDatos() {
        System.out.println("Alumno[" + this.getCodigo() + ", " + this.getNombre() + " " +
                this.getApellidos() + " , " + this.getPoblacion() + ", " +
                this.getFechaNacimiento().toString() + "]");
    }
}