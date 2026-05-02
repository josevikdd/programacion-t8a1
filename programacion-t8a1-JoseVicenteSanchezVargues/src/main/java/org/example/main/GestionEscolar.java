package org.example.main;

import org.example.DAO.CursoDAO;
import org.example.DAO.FactoriaDAO;
import org.example.modelo.*;
import org.example.utils.ConexionBD;
import org.example.utils.DatosEstaticos;
import org.example.utils.InputUtils;

import java.time.LocalDate;
import java.util.*;

public class GestionEscolar {

    // Se hace final para que no se pueda cambiar ni reasignar de nuevo
    private static final List<Curso> cursos = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);
    // Cargamos los datos
    private static final DatosEstaticos de = new DatosEstaticos();
    //Creamos los objetos DAO
    private static CursoDAO cursoDAO = FactoriaDAO.getCursoDAO();

    public static void main(String[] args) {

        //Crear metodo para cargar cursos y comprobar que funciona.
        cargarCursos();
        //Metodo para probar añadir y actualizar
        //añadirActualizar();

        int opcion;

        do {
            menu();
            opcion = InputUtils.readInt(sc, "Selecciona una opción: ");

            switch (opcion) {

                case 1 -> altaCurso();
                case 2 -> bajaCurso();
                case 3 -> altaAsignatura();
                case 4 -> bajaAsignatura();
                case 5 -> altaDocencia();
                case 6 -> bajaDocencia();
                case 7 -> altaAlumno();
                case 8 -> bajaAlumno();
                case 9 -> ponerNota();
                case 10 -> listarCursos();
                case 11 -> listarAsignaturasCurso();
                case 12 -> listarProfesoresCurso();
                case 13 -> listarAlumnosCurso();
                case 14 -> listarAlumnosAsignaturaProfesor();
                case 15 -> listarNotasAlumno();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    private static void cargarCursos() {
        List<Curso> cursostest = cursoDAO.getAll();

        if ((cursostest != null) && (!cursostest.isEmpty())){
            System.out.println("********************");
            System.out.println("*** LISTA DE CURSOS ****");
            System.out.println("********************");

            for (Curso curso : cursostest) {
                System.out.println(curso.toString());
            }
        }
        else {
            System.out.println("No es posible mostrar la lista de cursos.");
        }
    }

    private static void añadirActualizar(){
        System.out.println("'si' para agregar el curso 3ºDAM y cambiar 2º por 4º");
        if(sc.nextLine().equals("si")){
            Curso curso = new Curso(3, "3ºdam");
            int i = cursoDAO.add(curso);
            if (i>0){
                System.out.println("se ha agregado el curso");
            }
            else if (i<0){
                System.out.println("No se ha agregado el curso");
            }

            System.out.println("-------- a ver si podemos editar 2º----------");

            curso = cursoDAO.findById(Long.valueOf(2));
            curso.setDescripcion("4ºdam");
            cursoDAO.update(curso);
        }
    }

    private static void menu() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Alta curso");
        System.out.println("2. Baja curso");
        System.out.println("3. Alta asignatura");
        System.out.println("4. Baja asignatura");
        System.out.println("5. Alta docencia");
        System.out.println("6. Baja docencia");
        System.out.println("7. Alta alumno");
        System.out.println("8. Baja alumno");
        System.out.println("9. Poner nota");
        System.out.println("10. Listar cursos");
        System.out.println("11. Listar asignaturas de un curso");
        System.out.println("12. Listar profesores del curso");
        System.out.println("13. Listar alumnos del curso");
        System.out.println("14. Listar alumnos de las asignaturas de un profesor");
        System.out.println("15. Listar notas del alumno");
        System.out.println("0. Salir");
        System.out.print("");
    }

    /**
     * Métodos get/set necesarios para trabajar
     */

    public static List<Curso> getCursos() {
        return cursos;
    }

    /**
     * Dar de alta un curso
     */
    private static void altaCurso() {
        int codigo = InputUtils.readInt(sc, "Código del curso: ");
        String nombre = InputUtils.readString(sc, "Nombre del curso: ");

        if (buscarCurso(codigo) != null) {
            System.out.println("ERROR: Ya existe un curso con ese código.");
        } else {
            cursos.add(new Curso(codigo, nombre));
            System.out.println("Curso dado de alta.");
        }
    }

    /**
     * Baja de curso (borrado restrictivo)
     */
    private static void bajaCurso() {
        int codigo = InputUtils.readInt(sc, "Código del curso a borrar: ");

        Curso c = buscarCurso(codigo);
        if (buscarCurso(codigo) == null) {
            // El curso no existe, por lo mostramos un mensaje de error
            System.out.println("El curso indicado no existe");
        } else {
            // Borrado restrictivo: Comprobamos que el curso no tiene profesores ni alumnos
            if (c.getPersonas().size() > 0) {
                System.out.println("El curso indicado tiene profesores y/o alumnos asociados.");
            }
            // Comprobamos que no tiene asignaturas
            else if (c.getAsignaturas().size() > 0) {
                System.out.println("El curso indicado tiene asignaturas asociadas.");
            }
            //  Como no tiene nada asociado procedemos al borrado
            else {
                cursos.remove(c);
                System.out.println("Curso borrado correctamente.");
            }
        }
    }

    /**
     * Buscar Curso
     */
    private static Curso buscarCurso(int codigo) {
        // Creamos un objeto curso al que solo le pasamos el código
        Curso c = new Curso();
        c.setCodigo(codigo);
        // Usamos contains para comprobar si existe el curso. Como hemos implementado la
        // comprobación por código solo necesitamos pasarle el código
        if (cursos.contains(c)) {
            // Obtenemos la posición donde se encuentra y devolvemos de la lista de cursos esa posicion
            int pos = cursos.indexOf(c);
            return cursos.get(pos);
        } else {
            return null;
        }
    }

    /**
     * Alta asignatura
     */
    private static void altaAsignatura() {

        int codigo = InputUtils.readInt(sc, "Código del curso: ");

        Curso curso = buscarCurso(codigo);
        if (curso == null) {
            System.out.println("Curso no encontrado.");
        } else {
            int codigoAsig = InputUtils.readInt(sc, "Código de la asignatura: ");
            String nombre = InputUtils.readString(sc, "Nombre de la asignatura: ");
            Asignatura asignatura = buscarAsignatura(codigoAsig);
            if (asignatura != null) {
                System.out.println("ERROR: La asignatura ya existe.");
            } else {
                asignatura = new Asignatura(codigoAsig, nombre);
                curso.asignarAsignatura(asignatura);
                System.out.println("Asignatura dada de alta.");
            }
        }
    }

    /**
     * Baja asignatura
     */
    private static void bajaAsignatura() {

        int codigo = InputUtils.readInt(sc, "Código de la asignatura a borrar: ");

        // Como la asignatura solo puede estar en un curso al borrarla del curso se borrará

        // Localizamos la asignatura
        Asignatura asignatura = buscarAsignatura(codigo);
        if (asignatura != null) {
            // PROCESO NO RECOMENDABLE: No usar esto ya que al ir borrando cambia el índice de la lista
            /*
            for(int i = 0; i < asignatura.getExamenes().size(); i++){
                // Primero borramos al alumno el examen
                Examen examen = asignatura.getExamenes().get(i);
                Alumno alumno = examen.getAlumno();
                int pos = alumno.getExamenes().indexOf(examen);
                alumno.getExamenes().remove(pos);
                // Tras quitarle al alumno el examen borramos el examen de la asignatura
                asignatura.getExamenes().remove(i);
            */

            // PROCESO RECOMENDABLE: Utilizando un iterator, por un lado vamos a borrar los examenes
            // del alumno y luego borrar todos los examenes de la asignatura
            Iterator<Examen> it = asignatura.getExamenes().iterator();

            while (it.hasNext()) {
                Examen examen = it.next();
                Alumno alumno = examen.getAlumno();
                alumno.getExamenes().remove(examen);
                // Borramos el examen de la asignatura
                it.remove();
            }

            // Una vez borrados los examenes procedemos a borrar la asignatura del curso
            // y con esto, al perderse la relación se pierde la asignatura
            Curso curso = asignatura.getCurso();
            curso.getAsignaturas().remove(asignatura);
            System.out.println("Asignatura eliminada.");
        } else {
            System.out.println("No existe la asignatura.");
        }
    }

    /**
     * Buscar asignatura
     * En este caso vamos a recorrer la lista de cursos buscando
     * la asignatura en cada uno de ellos. Si la encuentra la devuelve
     */
    private static Asignatura buscarAsignatura(int codigo) {
        // Creamos un objeto Asignatura al que le asignamos el id para poder realizar las busquedas
        Asignatura asignatura = new Asignatura();
        asignatura.setCodigo(codigo);
        // Variable para parar el while cuando la encuentra
        boolean enc = false;
        // Variabla para recorrer la lista de cursos
        int i = 0;
        // Variable para almacenar la posicion de la asignatura en el curso
        int posicion = 0;
        while ((i < cursos.size()) && (enc == false)) {
            // Comprobamos si la asignatura está en la lista de asignaturas de un curso en concreto
            Curso c = cursos.get(i);
            if (c.getAsignaturas().contains(asignatura)) {
                // Si la encontramos la devolvemos a la variable asignatura mediante la posicion
                posicion = c.getAsignaturas().indexOf(asignatura);
                asignatura = c.getAsignaturas().get(posicion);
                // cambiamos enc para que el bucle pare
                enc = true;
            }
            // Si no lo encuentra aumentamos el indice
            else {
                i++;
            }
        }
        // Comprobamos si lo ha encontrado o no
        if (enc == true) {
            return asignatura;
        } else {
            return null;
        }
    }

    /** Comprobar si existe un profesor en la lista de personas
     */
    private static boolean existeProfesor(List<Persona> lista) {
        int i = 0;
        boolean encontrado = false;

        while (i < lista.size() && !encontrado) {
            if (lista.get(i) instanceof Profesor) {
                encontrado = true;
            }
            i++;
        }

        return encontrado;
    }

    /** Buscar un profesor por código en una lista de personas en concreto
     */
    private static Profesor buscarProfesor(int codigo, List<Persona> lista) {
        int i = 0;
        boolean encontrado = false;

        while (i < lista.size() && !encontrado) {
            if ((lista.get(i) instanceof Profesor)&&(lista.get(i).getCodigo()==codigo)) {
                encontrado = true;
            }else {
                i++;
            }
        }
        if (encontrado==true){
            return (Profesor) lista.get(i);
        }else{
            return null;
        }
    }

    /**
     * Permite seleccionar un profesor de un curso
     */
    private static Profesor seleccionarProfesor(Curso curso) {
        // Mostramos la lista de profesores
        System.out.println("=========================================");
        System.out.println("Lista de profesores del curso.");
        System.out.println("=========================================");
        for (int i = 0; i < curso.getPersonas().size(); i++) {
            if (curso.getPersonas().get(i) instanceof Profesor) {
                Profesor p = (Profesor) curso.getPersonas().get(i);
                p.mostrarDatos();
            }
        }
        Profesor profesor = null;
        // Vamos pidiendo un profesor hasta tenerlo
        while (profesor == null) {
            int codigoProfesor = InputUtils.readInt(sc, "Seleccione un código de profesor de los mostrados: ");
            profesor = buscarProfesor(codigoProfesor, curso.getPersonas());
            if (profesor == null) {
                System.out.println("Error: Profesor no encontrado.");
            }
        }
        return profesor;
    }

    /** Comprobar si existe un alumno en la lista de personas
     */
    private static boolean existeAlumno(List<Persona> lista) {
        int i = 0;
        boolean encontrado = false;

        while (i < lista.size() && !encontrado) {
            if (lista.get(i) instanceof Alumno) {
                encontrado = true;
            }
            i++;
        }

        return encontrado;
    }

    /**
     * Buscar alumno en todos los cursos
     */
    private static Alumno buscarAlumno(int codigo) {
        // Creamos una variable alumno para buscarla en los cursos
        Alumno alumno = new Alumno();
        alumno.setCodigo(codigo);
        // Variable para parar el while cuando la encuentra
        boolean enc = false;
        // Variabla para recorrer la lista de cursos
        int i = 0;
        // Variable para almacenar la posicion de la asignatura en el curso
        int posicion = 0;
        while ((i < cursos.size()) && (enc == false)) {
            // Comprobamos si el alumno está en un curso en concreto
            Curso c = cursos.get(i);
            if (c.getPersonas().contains(alumno)) {
                // Si la encontramos la devolvemos a la variable asignatura mediante la posicion
                posicion = c.getPersonas().indexOf(alumno);
                alumno = (Alumno) c.getPersonas().get(posicion);
                // cambiamos enc para que el bucle pare
                enc = true;
            }
            // Si no lo encuentra aumentamos el indice
            else {
                i++;
            }
        }
        // Comprobamos si lo ha encontrado o no
        if (enc == true) {
            return alumno;
        } else {
            return null;
        }
    }

    /**
     * Buscar alumno en una lista en concreto
     **/
    private static Alumno buscarAlumno(int codigo, List<Persona> lista) {
        int i = 0;
        boolean encontrado = false;

        while (i < lista.size() && !encontrado) {
            if ((lista.get(i) instanceof Alumno)&&(lista.get(i).getCodigo()==codigo)) {
                encontrado = true;
            }else {
                i++;
            }
        }
        if (encontrado==true){
            return (Alumno) lista.get(i);
        }else{
            return null;
        }
    }

    /**
     * Permite seleccionar un alumno de un curso
     */
    private static Alumno seleccionarAlumno(Curso curso) {
        // Mostramos la lista de alumnos
        System.out.println("=========================================");
        System.out.println("Lista de alumnos del curso.");
        System.out.println("=========================================");
        for (int i = 0; i < curso.getPersonas().size(); i++) {
            if (curso.getPersonas().get(i) instanceof Alumno) {
                Alumno p = (Alumno) curso.getPersonas().get(i);
                p.mostrarDatos();
            }
        }
        Alumno alumno = null;
        // Vamos pidiendo un alumno hasta tenerlo
        while (alumno == null) {
            int codigoAlumno = InputUtils.readInt(sc, "Seleccione un código de alumno de los mostrados: ");
            alumno = buscarAlumno(codigoAlumno, curso.getPersonas());
            if (alumno == null) {
                System.out.println("Error: Alumno no encontrado.");
            }
        }
        return alumno;
    }

    /** Comprobar si existen asignaturas, libres o ocupadas, en la lista de asignaturas
     */
    private static boolean existenAsignaturas(List<Asignatura> lista, boolean libre) {
        int i = 0;
        boolean encontrado = false;

        // Comprobamos si buscamos asignaturas libres
        if (libre) {
            while (i < lista.size() && !encontrado) {
                if (lista.get(i).getProfesor() == null) {
                    encontrado = true;
                }
                i++;
            }
        }
        // Buscamos asignaturas ocupadas
        else {
            while (i < lista.size() && !encontrado) {
                if (lista.get(i).getProfesor() != null) {
                    encontrado = true;
                }
                i++;
            }

        }

        return encontrado;
    }

    /**
     * Permite seleccionar una asignatura de un curso, controlando si es libre o ocupada
     */
    private static Asignatura seleccionarAsignaturaCurso(Curso curso, boolean libre) {
        // Mostramos la lista de asignaturas libres
        System.out.println("======================================================================================");
        System.out.println("Lista de asignaturas libres del curso " + curso.getCodigo() + " - " + curso.getDescripcion());
        System.out.println("======================================================================================");
        // Mostramos las asignaturas libres de profesor
        if (libre) {
            for (int i = 0; i < curso.getAsignaturas().size(); i++) {
                if (curso.getAsignaturas().get(i).getProfesor() == null) {
                    Asignatura a = curso.getAsignaturas().get(i);
                    a.mostrarDatos();
                }
            }
        } else {
            // Mostramos la lista de asignaturas asignadas a un profesor
            for (int i = 0; i < curso.getAsignaturas().size(); i++) {
                if (curso.getAsignaturas().get(i).getProfesor() != null) {
                    Asignatura a = curso.getAsignaturas().get(i);
                    a.mostrarDatos();
                }
            }
        }
        Asignatura asignatura = null;
        // Vamos pidiendo un profesor hasta tenerlo
        while (asignatura == null) {
            int codigoAsignatura = InputUtils.readInt(sc, "Seleccione un código de asignatura de los mostrados: ");
            asignatura = new Asignatura();
            asignatura.setCodigo(codigoAsignatura);
            asignatura = curso.getAsignaturas().get(curso.getAsignaturas().indexOf(asignatura));
            if (asignatura != null) {
                System.out.println("Error: Asignatura no encontrada.");
            }
        }
        return asignatura;
    }

    /**
     * Permite seleccionar una asignatura de las que imparte un profesor en concreto
     */
    private static Asignatura seleccionarAsignaturaProfesor(Profesor profesor) {
        // Mostramos la lista de asignaturas del profesor
        System.out.println("======================================================================================");
        System.out.println("Lista de asignaturas del profesor " + profesor.getNombre() + " " + profesor.getApellidos());
        System.out.println("======================================================================================");
        // Mostramos las asignaturas libres de profesor
        for (int i = 0; i < profesor.getAsignaturas().size(); i++) {
            Asignatura a = profesor.getAsignaturas().get(i);
            a.mostrarDatos();
        }
        Asignatura asignatura = null;
        // Vamos pidiendo una asignatura hasta tenerla
        while (asignatura == null) {
            int codigoAsignatura = InputUtils.readInt(sc, "Seleccione un código de asignatura de los mostrados: ");
            asignatura = new Asignatura();
            asignatura.setCodigo(codigoAsignatura);
            asignatura = profesor.getAsignaturas().get(profesor.getAsignaturas().indexOf(asignatura));
            if (asignatura == null) {
                System.out.println("Error: Asignatura no encontrada.");
            }
        }
        return asignatura;
    }

    /**
     * Poner nota
     */
    private static void ponerNota() {
        boolean seguir = true;
        // Buscamos el curso
        int codigoCurso = InputUtils.readInt(sc, "Indique el código del curso: ");
        Curso curso = buscarCurso(codigoCurso);
        Profesor profesor = null;
        Asignatura asignatura = null;
        Alumno alumno = null;
        if (curso != null) {
            // Comprobamos si existe algún profesor
            if (existeProfesor(curso.getPersonas())==false) {
                // Si no hay profesores se informa y se sale
                System.out.println("No hay profesores asociados al curso.");
                return;
            } else {
                profesor = seleccionarProfesor(curso);
            }

        } else {
            System.out.println("No se ha encontrado el curso indicado.");
            return;
        }

        // Ya tenemos el curso y el profesor, por lo que mostramos las asignaturas que tiene
        if ((curso != null) && (profesor != null)) {
            boolean encAsig = false;
            System.out.println("======================================================================================");
            System.out.println("Lista de asignaturas del profesor " + profesor.getNombre() + " " + profesor.getApellidos());
            System.out.println("======================================================================================");
            // Comprobamos que el profesor tiene asignaturas en el curso. Como tenemos que recorrer todas usamos for
            for (int i = 0; i < profesor.getAsignaturas().size(); i++) {
                Asignatura a = profesor.getAsignaturas().get(i);
                // Comprobamos que son iguales
                if (a.getCurso().equals(curso)) {
                    a.mostrarDatos();
                    encAsig = true;
                }
            }
            // Si no tiene asignaturas sale del proceso
            if (encAsig == false) {
                System.out.println("No tiene asignaturas asignadas al profesor.");
                return;
            } else {
                while (asignatura == null) {
                    int codigoAsignatura = InputUtils.readInt(sc, "Seleccione un código de asignatura de los mostrados: ");
                    asignatura = new Asignatura();
                    asignatura.setCodigo(codigoAsignatura);
                    asignatura = profesor.getAsignaturas().get(profesor.getAsignaturas().indexOf(asignatura));
                    if (asignatura == null) {
                        System.out.println("Error: Asignatura no encontrado.");
                    }
                }
            }

            // Ya tenemos la asignatura, por lo que mostramos los alumnos matriculados
            if (asignatura != null) {
                if (asignatura.getAlumnos().size() <= 0) {
                    System.out.println("No tiene alumnos asignados a la asignatura.");
                    return;
                } else {
                    // No saldremos de la lista de alumnos, finalizando el proceso, hasta escribir -1
                    boolean salir = false;
                    while (salir == false) {
                        System.out.println("Lista de alumnos matriculados en la asignatura " + asignatura.getNombre());
                        System.out.println("===============================================================================");
                        // Los alumnos solo están matriculados en un único curso así que no hace falta comprobar de qué curso son.
                        for (int i = 0; i < asignatura.getAlumnos().size(); i++) {
                            Alumno a = asignatura.getAlumnos().get(i);
                            a.mostrarDatos();
                        }
                        while ((alumno == null) && (salir == false)) {
                            int codigoAlumno = InputUtils.readInt(sc, "Seleccione un código de alumno de los mostrados: ");
                            if (codigoAlumno == -1) {
                                salir = true;
                            } else {
                                alumno = new Alumno();
                                alumno.setCodigo(codigoAlumno);
                                alumno = asignatura.getAlumnos().get(asignatura.getAlumnos().indexOf(alumno));
                                if (alumno == null) {
                                    System.out.println("Error: Alumno no encontrado.");
                                }
                            }
                        }
                        // En este momento ya tenemos la asignatura y el alumno matriculado, por lo que
                        // podemos pasar a pedir el codigo, la fecha y la nota, controlando que sea entre 0 y 10
                        int codigoExamen = InputUtils.readInt(sc, "Introduzca el código del examen: ");
                        LocalDate fecha = InputUtils.readLocalDate(sc, "Introduzca la fecha del examen: ");
                        float nota = -1;
                        while ((nota < 0) || (nota > 11)) {
                            nota = InputUtils.readInt(sc, "Introduzca la nota del examen entre 0 y 10: ");
                            if ((nota < 0) || (nota > 11)) {
                                System.out.println("Nota incorrecta.");
                            }
                        }
                        if(buscarExamen(codigoExamen)==null) {
                            // Creamos el examen y lo asignamos al alumno
                            Examen examen = new Examen(codigoExamen, alumno, asignatura, fecha, nota);

                            System.out.println("Nota asignada correctamente");
                        }else{
                            System.out.println("ERROR: El examen ya existe en un curso.");
                        }
                        salir=true;
                    }
                }
            }
        }
    }

    /**
     * Buscar examen en todos los cursos
     */
    private static Examen buscarExamen(int codigo) {
        // Creamos una variable examen para buscarla en los cursos
        Examen examen = new Examen();
        examen.setCodigo(codigo);
        // Variable para parar el while cuando la encuentra
        boolean enc = false;
        // Variabla para recorrer la lista de cursos
        int i = 0;
        // Variable para almacenar la posicion de la asignatura en el curso
        int posicion = 0;
        while ((i < cursos.size()) && (enc == false)) {
            // De cada curso recorremos su lista de asignaturas buscando el examen
            int j = 0;
            Curso c = cursos.get(i);
            while((j<c.getAsignaturas().size())&&(enc==false)){
                Asignatura asig = c.getAsignaturas().get(j);
                if (asig.getExamenes().contains(examen)) {
                    enc = true;
                    // Si la encontramos la devolvemos a la variable asignatura mediante la posicion
                    posicion = asig.getExamenes().indexOf(examen);
                    examen = asig.getExamenes().get(posicion);
                }else{
                    j++;
                }
            }
            i++;
        }
        // Comprobamos si lo ha encontrado o no
        if (enc == true) {
            return examen;
        } else {
            return null;
        }
    }

    /**
     * Alta docencia
     */
    private static void altaDocencia() {

        int codigo = InputUtils.readInt(sc, "Código del curso: ");

        Curso curso = buscarCurso(codigo);
        Profesor profesor = null;
        if (curso == null) {
            System.out.println("Curso no encontrado.");
        } else {
            // Comprobamos si existe algún profesor
            if (existeProfesor(curso.getPersonas())==false) {
                System.out.println("No hay profesores asociados al curso.");
                return;
            } else {
                // Seleccionamos un profesor que tenga menos de 2 asignaturas asignadas
                while (profesor == null) {
                    profesor = seleccionarProfesor(curso);
                    if (profesor.getAsignaturas().size() >= 2) {
                        System.out.println("No puede seleccionar el profesor ya que tiene 2 o más asignaturas asignadas.");
                        profesor = null;
                    }
                }
            }
        }

        // Como tenemos curso y profesor seleccionamos la lista de asignaturas que no tiene profesor del curso
        if ((curso != null) && (profesor != null)) {
            if (existenAsignaturas(curso.getAsignaturas(), true)) {
                // Seleccionamos la asignatura
                Asignatura asignatura = seleccionarAsignaturaCurso(curso, true);
                // Asignamos a la asignatura el profesor y viceversa
                asignatura.impartir(profesor);
                System.out.println("Profesor asignado a la asignatura correctamente.");
            } else {
                System.out.println("No hay asignaturas libres en este curso.");
            }
        }
    }

    /**
     * Baja docencia
     */
    private static void bajaDocencia() {

        int codigo = InputUtils.readInt(sc, "Código del curso: ");

        Curso curso = buscarCurso(codigo);
        Profesor profesor = null;
        if (curso == null) {
            System.out.println("Curso no encontrado.");
        } else {
            // Comprobamos si existe algún profesor
            if (existeProfesor(curso.getPersonas())==false) {
                System.out.println("No hay profesores asociados al curso.");
                return;
            } else {
                // Seleccionamos un profesor que tenga asignaturas asignadas
                while (profesor == null) {
                    profesor = seleccionarProfesor(curso);
                    if (profesor.getAsignaturas().size() < 0) {
                        System.out.println("No puede seleccionar el profesor ya que no tiene asignaturas asignadas.");
                        profesor = null;
                    }
                }
            }
        }
        // Como tenemos curso y profesor mostramos la lista de asignaturas del profesor del curso
        if ((curso != null) && (profesor != null)) {
            // Seleccionamos la asignatura
            Asignatura asignatura = seleccionarAsignaturaProfesor(profesor);
            // Desasignamos la asignatura del profesor y viceversa
            asignatura.dejarDeImpartir(profesor);
            System.out.println("Profesor desasignado a la asignatura correctamente.");
        }
    }

    /**
     * Alta alumno
     */
    private static void altaAlumno() {
        int codigoCurso = InputUtils.readInt(sc, "Código del curso: ");

        Curso curso = buscarCurso(codigoCurso);
        Alumno alumno = null;
        if (curso == null) {
            System.out.println("Curso no encontrado.");
        } else {
            int codigo = InputUtils.readInt(sc, "Introduzca el código del alumno: ");
            String nombre = InputUtils.readString(sc, "Introduzca el nombre del alumno: ");
            String apellidos = InputUtils.readString(sc, "Introduzca los apellidos del alumno: ");
            String poblacion = InputUtils.readString(sc, "Introduzca la población del alumno: ");
            LocalDate fechaNacimiento = InputUtils.readLocalDate(sc, "Introduzca la fecha de nacimiento del alumno: ");
            alumno = buscarAlumno(codigo);
            // Comprobamos si no existe el alumno
            if(alumno==null){
                alumno = new Alumno(codigo, nombre, apellidos, poblacion, fechaNacimiento);
                curso.asignarPersona(alumno);
                System.out.println("Alumno insertado correctamente en el curso.");
            }
            // Si el alumno existe debemos comprobar si está en el curso correcto
            else{
                // Comprobamos si el alumno está en el curso adecuado, sino sale del proceso
                if (alumno.getCurso() != curso) {
                    System.out.println("El alumno existe en otro curso distinto.");
                } else {
                    alumno.setNombre(nombre);
                    alumno.setApellidos(apellidos);
                    alumno.setPoblacion(poblacion);
                    alumno.setFechaNacimiento(fechaNacimiento);
                    System.out.println("Alumno actualizado correctamente.");
                }
            }
        }
    }

    /**
     * Baja alumno
     */
    public static void bajaAlumno() {
        int codigoCurso = InputUtils.readInt(sc, "Código del curso: ");

        Curso curso = buscarCurso(codigoCurso);
        Alumno alumno = null;
        if (curso == null) {
            System.out.println("Curso no encontrado.");
        } else {
            int codigo = InputUtils.readInt(sc, "Introduzca el código del alumno: ");
            alumno = buscarAlumno(codigo);
            // Comprobamos si el alumno está en el curso adecuado, sino sale del proceso
            if (alumno.getCurso() != curso) {
                System.out.println("El alumno existe en otro curso distinto.");
            } else {
                // Eliminamos los examenes de las asignatura.
                // Para ello usamos un iterator para poder borrar sin problemas
                Iterator<Examen> it = alumno.getExamenes().iterator();

                while (it.hasNext()) {
                    Examen examen = it.next();
                    Asignatura asignatura = examen.getAsignatura();
                    // Borramos el examen de la asignatura y de la lista de alumnos
                    asignatura.getExamenes().remove(examen);
                    asignatura.getAlumnos().remove(alumno);
                    it.remove();
                }

                // Eliminamos al alumno de las asignaturas
                for(int i=0;i<curso.getAsignaturas().size();i++){
                    Asignatura asig = curso.getAsignaturas().get(i);
                    asig.getAlumnos().remove(alumno);
                }

                curso.desasignarPersona(alumno);
                System.out.println("Alumno borrado correctamente.");
            }
        }
    }

    /** Listar cursos **/
    private static void listarCursos(){
        System.out.println("============================================================");
        System.out.println("Listado de cursos.");
        System.out.println("============================================================");
        for(int i=0;i<cursos.size();i++){
            System.out.println(cursos.get(i).toString());
        }
    }

    /** Listar asignaturas de un curso **/
    private static void listarAsignaturasCurso() {
        int codigoCurso = InputUtils.readInt(sc, "Código del curso: ");

        Curso curso = buscarCurso(codigoCurso);
        if (curso == null) {
            System.out.println("Curso no encontrado.");
        } else {
            if(curso.getAsignaturas().size()>0) {
                System.out.println("============================================================");
                System.out.println("Listado de asignaturas del curso " + curso.getDescripcion());
                System.out.println("============================================================");
                for (int i = 0; i < curso.getAsignaturas().size(); i++) {
                    curso.getAsignaturas().get(i).mostrarDatos();
                }
            }else{
                System.out.println("El curso no tiene asignaturas.");
            }
        }
    }

    /** Listar profesores de un curso **/
    private static void listarProfesoresCurso(){
        int codigoCurso = InputUtils.readInt(sc, "Código del curso: ");

        Curso curso = buscarCurso(codigoCurso);
        if (curso == null) {
            System.out.println("Curso no encontrado.");
        } else {
            // Comprobamos que tiene profesores
            if(existeProfesor(curso.getPersonas())) {
                // Mostramos la lista de profesores
                System.out.println("============================================================");
                System.out.println("Lista de profesores del curso.");
                System.out.println("============================================================");
                for (int i = 0; i < curso.getPersonas().size(); i++) {
                    if (curso.getPersonas().get(i) instanceof Profesor) {
                        Profesor p = (Profesor) curso.getPersonas().get(i);
                        p.mostrarDatos();
                    }
                }
            }else{
                System.out.println("El curso no tiene profesores asociados.");
            }
        }
    }

    /** Listar alumnos de un curso **/
    private static void listarAlumnosCurso() {
        int codigoCurso = InputUtils.readInt(sc, "Código del curso: ");

        Curso curso = buscarCurso(codigoCurso);
        if (curso == null) {
            System.out.println("Curso no encontrado.");
        } else {
            if(existeAlumno(curso.getPersonas())&&(curso.getPersonas().size()>0)){
                System.out.println("============================================================");
                System.out.println("Listado de alumnos del curso " + curso.getDescripcion());
                System.out.println("============================================================");
                for (int i = 0; i < curso.getPersonas().size(); i++) {
                    if(curso.getPersonas().get(i) instanceof Alumno) {
                        ((Alumno) curso.getPersonas().get(i)).mostrarDatos();
                    }
                }
            }else{
                System.out.println("El curso no tiene alumnos asignados.");
            }
        }
    }

    /**
     * Listar alumnos de la asignatura de un profesor
     */
    private static void listarAlumnosAsignaturaProfesor() {
        int codigoCurso = InputUtils.readInt(sc, "Código del curso: ");

        Curso curso = buscarCurso(codigoCurso);
        Profesor profesor = null;
        if (curso == null) {
            System.out.println("Curso no encontrado.");
        } else {
            // Comprobamos si existe algún profesor
            if (existeProfesor(curso.getPersonas())) {
                // Seleccionamos un profesor que tenga menos de 2 asignaturas asignadas
                profesor = seleccionarProfesor(curso);
                // Recorremos las asignaturas
                for(int i=0;i<profesor.getAsignaturas().size();i++){
                    Asignatura asignatura = profesor.getAsignaturas().get(i);
                    List<Alumno> listaAlumnos = asignatura.getAlumnos();
                    if(listaAlumnos.size()<=0){
                        System.out.println("No hay alumnos para la asignatura " + asignatura.getNombre());
                    }else{
                        System.out.println("============================================================");
                        System.out.println("Lista de alumnos de la asignatura " + asignatura.getNombre());
                        System.out.println("============================================================");
                        for(int j=0;j<listaAlumnos.size();j++){
                            listaAlumnos.get(j).mostrarDatos();
                        }
                    }
                }
            } else {
                System.out.println("No hay profesores asociados al curso.");
            }
        }
    }

    /**
     * Listar notas alumno
     */
    private static void listarNotasAlumno() {
        int codigoCurso = InputUtils.readInt(sc, "Código del curso: ");

        Curso curso = buscarCurso(codigoCurso);
        Alumno alumno = null;
        if (curso == null) {
            System.out.println("Curso no encontrado.");
        } else {
            alumno = seleccionarAlumno(curso);
            // Comprobamos si el alumno está en el curso adecuado, si no sale del proceso
            if (alumno.getCurso() != curso) {
                System.out.println("El alumno existe en otro curso distinto.");
            } else {
                if(alumno.getExamenes().size()>0) {
                    // Mostramos la lista de examenes y notas
                    System.out.println("============================================================================");
                    System.out.println("Lista de exámenes y notas del alumno " + alumno.getNombre() + " " + alumno.getApellidos());
                    System.out.println("============================================================================");
                    // Ordenamos la lista por fecha
                    Collections.sort(alumno.getExamenes());
                    for (int i = 0; i < alumno.getExamenes().size(); i++) {
                        Examen e = alumno.getExamenes().get(i);
                        System.out.println(e.toString());
                    }
                }else{
                    System.out.println("El alumno no tiene exámenes.");
                }
            }
        }
    }
}