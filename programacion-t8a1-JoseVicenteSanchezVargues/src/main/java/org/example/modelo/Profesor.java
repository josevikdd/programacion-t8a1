package org.example.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Profesor extends Persona implements Mostrable{

    private String telefono;
    private String categoria;

    // Relaciones
    // Se hace final para que no se pueda cambiar ni reasignar de nuevo
    private final List<Asignatura> asignaturas = new ArrayList<>();

    public Profesor(int codigo, String nombre, String apellidos,
                    String poblacion, LocalDate fechaNacimiento,
                    String telefono, String categoria) {
        super(codigo, nombre, apellidos, poblacion, fechaNacimiento);
        this.telefono = telefono;
        this.categoria = categoria;
    }

    @Override
    public void mostrarDatos() {
        System.out.println("Profesor[" + this.getCodigo() + ", " + this.getNombre() + " " +
                this.getApellidos() + " , " + this.getPoblacion() + ", " +
                this.getFechaNacimiento().toString() + ", " +
                this.telefono + ", " + this.categoria + "]");
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCategoria() {
        return categoria;
    }
}
