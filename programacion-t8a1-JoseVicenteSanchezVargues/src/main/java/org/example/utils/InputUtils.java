package org.example.utils;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputUtils {

    public static int readInt(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Debe ser un número entero (ej: 42). Inténtalo de nuevo.");
            }
        }
    }


    public static float readFloat(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String line = sc.nextLine().trim();

            try {
                return Float.parseFloat(line);
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Debe ser un número real (ej: 3.14). Inténtalo de nuevo.");
            }
        }
    }


    public static String readString(Scanner sc, String mensaje) {
        boolean salir = false;
        String linea="";
        while (salir==false) {
            System.out.print(mensaje);
            linea = sc.nextLine();
            if (!linea.isBlank()) {
                salir=true;
            }else {
                System.out.println("La cadena no puede estar vacía. Inténtalo de nuevo.");
            }
        }
        return linea;
    }

    public static LocalDate readLocalDate(Scanner sc, String mensaje) {
        boolean salir = false;
        LocalDate fecha = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (salir == false) {
            System.out.print(mensaje);
            String linea = sc.nextLine();

            if (!linea.isBlank()) {
                try {
                    fecha = LocalDate.parse(linea, formatter);
                    salir = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Fecha no válida. Usa el formato dd/MM/yyyy.");
                }
            } else {
                System.out.println("La fecha no puede estar vacía. Inténtalo de nuevo.");
            }
        }

        return fecha;
    }


}
