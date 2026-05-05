package org.example.main;

import org.example.DAO.alumno.AlumnoDAOImpl;
import org.example.DAO.asignatura.AsignaturaDAOImpl;
import org.example.DAO.curso.CursoDAOImpl;
import org.example.DAO.FactoriaDAO;
import org.example.DAO.examen.ExamenDAOImpl;
import org.example.DAO.profesor.ProfesorDAOImpl;
import org.example.modelo.*;
import org.example.utils.InputUtils;

import java.time.LocalDate;
import java.util.*;

public class GestionEscolar {

    // Se hace final para que no se pueda cambiar ni reasignar de nuevo
    private static final Scanner sc = new Scanner(System.in);
    //Creamos los objetos DAO
    private static CursoDAOImpl cursoDAOImpl = FactoriaDAO.getCursoDAO();
    private static AsignaturaDAOImpl asignaturaDAOImpl = FactoriaDAO.getAsignaturaDAO();
    private static ProfesorDAOImpl  profesorDAOImpl = FactoriaDAO.getProfesorDAO();
    private static AlumnoDAOImpl alumnoDAOImpl = FactoriaDAO.getAlumnoDAO();
    private static ExamenDAOImpl examenDAOImpl = FactoriaDAO.getExamenDAO();

    public static void main(String[] args) {

        int opcion;

        do {
            menu();
            opcion = InputUtils.readInt(sc, "Selecciona una opción: ");

            switch (opcion) {

                case 1 -> altaCurso();
                case 2 -> bajaCurso();
                case 3 -> altaAsignatura();
                case 4 -> bajaAsignatura();
                case 5 -> altaProfesor();
                case 6 -> bajaProfesor();
                case 7 -> altaDocencia();
                case 8 -> bajaDocencia();
                case 9 -> altaAlumno();
                case 10 -> bajaAlumno();
                case 11 -> ponerNota();
                case 12 -> listarCursos();
                case 13 -> listarAsignaturasCurso();
                case 14 -> listarProfesoresCurso();
                case 15 -> listarAlumnosCurso();
                case 16 -> listarAlumnosAsignaturaProfesor();
                case 17 -> listarNotasAlumno();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    private static void menu() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Alta curso");
        System.out.println("2. Baja curso");
        System.out.println("3. Alta asignatura");
        System.out.println("4. Baja asignatura");
        System.out.println("5. Alta Profesor");
        System.out.println("6. Baja Profesor");
        System.out.println("7. Alta docencia");
        System.out.println("8. Baja docencia");
        System.out.println("9. Alta alumno");
        System.out.println("10. Baja alumno");
        System.out.println("11. Poner nota");
        System.out.println("12. Listar cursos");
        System.out.println("13. Listar asignaturas de un curso");
        System.out.println("14. Listar profesores del curso");
        System.out.println("15. Listar alumnos del curso");
        System.out.println("16. Listar alumnos de las asignaturas de un profesor");
        System.out.println("17. Listar notas del alumno");
        System.out.println("0. Salir");
        System.out.print("");
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
            cursoDAOImpl.add(new Curso(codigo, nombre));
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
            // El curso no existe, por lo que mostramos un mensaje de error.
            System.out.println("El curso indicado no existe");
        } else {
            // Borrado restrictivo: Comprobamos que el curso no tiene profesores ni alumnos
            if (profesorDAOImpl.getAllByCurso(c).size() > 0 || alumnoDAOImpl.getAllByCurso(c).size() > 0) {
                System.out.println("El curso indicado tiene profesores y/o alumnos asociados.");
            }
            // Comprobamos que no tiene asignaturas
            else if (asignaturaDAOImpl.getAllByCurso(c).size() > 0) {
                System.out.println("El curso indicado tiene asignaturas asociadas.");
            }
            //  Como no tiene nada asociado procedemos al borrado
            else {
                cursoDAOImpl.deleteById(Long.valueOf(codigo));
                System.out.println("Curso borrado correctamente.");
            }
        }
    }

    /**
     * Buscar Curso
     */
    private static Curso buscarCurso(int codigo) {
        if (cursoDAOImpl.findById(Long.valueOf(codigo)) == null) {
            System.out.println("El curso indicado no existe");
            return null;
        }
        else {
            return cursoDAOImpl.findById(Long.valueOf(Long.valueOf(codigo)));
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
                asignatura.setCurso(curso);
                asignaturaDAOImpl.add(asignatura);
                System.out.println("Asignatura dada de alta.");
            }
        }
    }

    /**
     * Baja asignatura
     */
    private static void bajaAsignatura() {

        int codigo = InputUtils.readInt(sc, "Código de la asignatura a borrar: ");


        // Localizamos la asignatura
        Asignatura asignatura = buscarAsignatura(codigo);
        if (asignatura != null) {
            examenDAOImpl.deleteByAsignaturaId(Long.valueOf(codigo));
            asignaturaDAOImpl.deleteRelacionesById(Long.valueOf(codigo));
            asignaturaDAOImpl.deleteById(Long.valueOf(codigo));

            System.out.println("Asignatura eliminada.");
        } else {
            System.out.println("No existe la asignatura.");
        }
    }

    private static void altaProfesor() {
        int codigoCurso = InputUtils.readInt(sc, "Código del curso: ");

        Curso curso = buscarCurso(codigoCurso);
        Profesor profesor = null;
        if (curso == null) {
            System.out.println("Curso no encontrado.");
        } else {
            int codigo = InputUtils.readInt(sc, "Introduzca el código del profesor: ");
            String nombre = InputUtils.readString(sc, "Introduzca el nombre del profesor: ");
            String apellidos = InputUtils.readString(sc, "Introduzca los apellidos del profesor: ");
            String poblacion = InputUtils.readString(sc, "Introduzca la población del profesor: ");
            LocalDate fechaNacimiento = InputUtils.readLocalDate(sc, "Introduzca la fecha de nacimiento del profesor: ");
            String telefono = InputUtils.readString(sc, "Introduzca el teléfono del profesor: ");
            String categoria = InputUtils.readString(sc, "Introduzca la categoria del profesor: ");
            profesor = profesorDAOImpl.findById(Long.valueOf(codigo));

            if(profesor==null){
                profesor = new Profesor (codigo, nombre, apellidos, poblacion, fechaNacimiento, telefono, categoria);
                profesor.setCurso(curso);
                profesorDAOImpl.add(profesor);

                System.out.println("Profesor insertado correctamente en el curso.");
            }
            else{
                System.out.println("Ya existe un profesor con el código ingresado.");
            }
        }
    }

    private static void bajaProfesor() {
        int codigoCurso = InputUtils.readInt(sc, "Código del curso: ");

        Profesor profesor = null;
        profesor = seleccionarProfesor(cursoDAOImpl.findById(Long.valueOf(codigoCurso)));

        if (profesor != null) {
            asignaturaDAOImpl.deleteProfesor(profesor);
            profesorDAOImpl.deleteById(Long.valueOf(profesor.getCodigo()));
            System.out.println("Profesor eliminado correctamente.");
        }
        else {
            System.out.println("Error: Profesor no encontrado.");
        }
    }

    /**
     * Buscar asignatura
     * En este caso vamos a recorrer la lista de cursos buscando
     * la asignatura en cada uno de ellos. Si la encuentra la devuelve
     */
    private static Asignatura buscarAsignatura(int codigo) {
        if (asignaturaDAOImpl.findById(Long.valueOf(codigo)) == null) {
            System.out.println("La asignatura indicada no existe");
            return null;
        }
        else {
            return asignaturaDAOImpl.findById(Long.valueOf(Long.valueOf(codigo)));
        }
    }

    /** Buscar un profesor por código en una lista de personas en concreto
     */
    private static Profesor buscarProfesor(int codigo, List<Profesor> profesores) {
        int i = 0;
        boolean encontrado = false;

        while (i < profesores.size() && !encontrado) {
            if ((profesores.get(i) instanceof Profesor)&&(profesores.get(i).getCodigo()==codigo)) {
                encontrado = true;
            }else {
                i++;
            }
        }
        if (encontrado==true){
            return profesores.get(i);
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
        List<Profesor> profesores = profesorDAOImpl.getAllByCurso(curso);
        for (Profesor profesor : profesores) {
            profesor.mostrarDatos();
        }
        Profesor profesor = null;
        // Vamos pidiendo un profesor hasta tenerlo
        while (profesor == null) {
            int codigoProfesor = InputUtils.readInt(sc, "Seleccione un código de profesor de los mostrados: ");
            profesor = buscarProfesor(codigoProfesor, profesores);
            if (profesor == null) {
                System.out.println("Error: Profesor no encontrado.");
            }
        }
        return profesor;
    }

    /**
     * Buscar alumno en una lista en concreto
     **/
    private static Alumno buscarAlumno(int codigo, List<Alumno> lista) {
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
            return lista.get(i);
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
        List <Alumno> alumnos = alumnoDAOImpl.getAllByCurso(curso);
        for (Alumno alumno : alumnos) {
            alumno.mostrarDatos();
        }
        Alumno alumno = null;
        // Vamos pidiendo un alumno hasta tenerlo
        while (alumno == null) {
            int codigoAlumno = InputUtils.readInt(sc, "Seleccione un código de alumno de los mostrados: ");
            alumno = buscarAlumno(codigoAlumno, alumnos);
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
    private static Asignatura seleccionarAsignaturaCurso(Curso curso, boolean libre, List<Asignatura> asignaturas) {
        // Mostramos la lista de asignaturas libres
        System.out.println("======================================================================================");
        System.out.println("Lista de asignaturas libres del curso " + curso.getCodigo() + " - " + curso.getDescripcion());
        System.out.println("======================================================================================");
        // Mostramos las asignaturas libres de profesor
        if (libre) {
            for (int i = 0; i < asignaturas.size(); i++) {
                if (asignaturas.get(i).getProfesor() == null) {
                    Asignatura a = asignaturas.get(i);
                    a.mostrarDatos();
                }
            }
        } else {
            // Mostramos la lista de asignaturas asignadas a un profesor
            for (int i = 0; i < asignaturas.size(); i++) {
                if (asignaturas.get(i).getProfesor() != null) {
                    Asignatura a = asignaturas.get(i);
                    a.mostrarDatos();
                }
            }
        }
        Asignatura asignatura = null;
        // Vamos pidiendo un profesor hasta tenerlo
        while (asignatura == null) {
            int codigoAsignatura = InputUtils.readInt(sc, "Seleccione un código de asignatura de los mostrados: ");
            asignatura = new Asignatura();
            asignatura = asignaturaDAOImpl.findById(Long.valueOf(codigoAsignatura));
            if (asignatura == null) {
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
        for (int i = 0; i < asignaturaDAOImpl.getAllByProfe(profesor).size(); i++) {
            Asignatura a = asignaturaDAOImpl.getAllByProfe(profesor).get(i);
            a.mostrarDatos();
        }
        Asignatura asignatura = null;
        // Vamos pidiendo una asignatura hasta tenerla
        while (asignatura == null) {
            int codigoAsignatura = InputUtils.readInt(sc, "Seleccione un código de asignatura de los mostrados: ");
            asignatura = new Asignatura();
            asignatura = asignaturaDAOImpl.findById(Long.valueOf(codigoAsignatura));
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
            if (profesorDAOImpl.getAllByCurso(curso).isEmpty()) {
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
            System.out.println("======================================================================================");
            System.out.println("Lista de asignaturas del profesor " + profesor.getNombre() + " " + profesor.getApellidos());
            System.out.println("======================================================================================");

            List<Asignatura> asignaturas = asignaturaDAOImpl.getAllByProfeCurso(profesor, curso);

            // Si no tiene asignaturas sale del proceso
            if (asignaturas.isEmpty()) {
                System.out.println("No tiene asignaturas asignadas al profesor.");
                return;
            } else {
                for (Asignatura asignaturaListada : asignaturas) {
                    asignaturaListada.mostrarDatos();
                }
                boolean seleccionada = false;
                while (!seleccionada) {
                    int codigoAsignatura = InputUtils.readInt(sc, "Seleccione un código de asignatura de los mostrados: ");
                    asignatura = asignaturaDAOImpl.findById(Long.valueOf(codigoAsignatura));
                    if (asignaturas.contains(asignatura)){
                        seleccionada = true;
                    }
                    if (!seleccionada) {
                        System.out.println("Error: Asignatura no encontrada.");
                    }
                }
            }

            // Ya tenemos la asignatura, por lo que mostramos los alumnos matriculados
            if (asignatura != null) {
                List<Alumno> alumnos = alumnoDAOImpl.getAllByAsignatura(asignatura);
                if (alumnos.size() <= 0) {
                    System.out.println("No tiene alumnos asignados a la asignatura.");
                    return;
                } else {
                    // No saldremos de la lista de alumnos, finalizando el proceso, hasta escribir -1
                    boolean salir = false;
                    while (salir == false) {
                        System.out.println("Lista de alumnos matriculados en la asignatura " + asignatura.getNombre());
                        System.out.println("===============================================================================");
                        // Los alumnos solo están matriculados en un único curso así que no hace falta comprobar de qué curso son.
                        for (Alumno alumnoListado : alumnos){
                            alumnoListado.mostrarDatos();
                        }
                        while ((alumno == null) && (salir == false)) {
                            int codigoAlumno = InputUtils.readInt(sc, "Seleccione un código de alumno de los mostrados: ");
                            if (codigoAlumno == -1) {
                                salir = true;
                            } else {
                                alumno = alumnoDAOImpl.findById(Long.valueOf(codigoAlumno));
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
                        if(examenDAOImpl.findById(Long.valueOf(codigoExamen))==null) {
                            // Creamos el examen y lo asignamos al alumno
                            Examen examen = new Examen(codigoExamen, alumno, asignatura, fecha, nota);
                            examenDAOImpl.add(examen);

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
            if (profesorDAOImpl.getAllByCurso(curso).isEmpty()) {
                System.out.println("No hay profesores asociados al curso.");
                return;
            } else {
                // Seleccionamos un profesor que tenga menos de 2 asignaturas asignadas
                while (profesor == null) {
                    profesor = seleccionarProfesor(curso);
                    if (asignaturaDAOImpl.getAllByProfe(profesor).size() >= 2) {
                        System.out.println("No puede seleccionar el profesor ya que tiene 2 o más asignaturas asignadas.");
                        profesor = null;
                    }
                }
            }
        }

        // Como tenemos curso y profesor seleccionamos la lista de asignaturas que no tiene profesor del curso
        if ((curso != null) && (profesor != null)) {
            if (existenAsignaturas(asignaturaDAOImpl.getAllByCurso(curso), true)) {
                // Seleccionamos la asignatura
                Asignatura asignatura = seleccionarAsignaturaCurso(curso, true, asignaturaDAOImpl.getAllByCurso(curso));
                // Asignamos a la asignatura el profesor y viceversa
                asignatura.impartir(profesor, curso);
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
            if (profesorDAOImpl.getAllByCurso(curso).isEmpty()) {
                System.out.println("No hay profesores asociados al curso.");
                return;
            } else {
                // Seleccionamos un profesor que tenga asignaturas asignadas
                while (profesor == null) {
                    profesor = seleccionarProfesor(curso);
                    if (asignaturaDAOImpl.getAllByProfe(profesor).size() < 0) {
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
            asignatura.dejarDeImpartir(profesor, curso);
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
            alumno = alumnoDAOImpl.findById(Long.valueOf(codigo));
            // Comprobamos si no existe el alumno
            if(alumno==null){
                alumno = new Alumno (codigo, nombre, apellidos, poblacion, fechaNacimiento);
                List <Asignatura> asignaturas = asignaturaDAOImpl.getAllByCurso(curso);
                alumnoDAOImpl.add(alumno, codigoCurso);
                for (Asignatura asignatura : asignaturas) {
                    alumnoDAOImpl.addRelaciones(alumno, asignatura.getCodigo());
                }
                System.out.println("Alumno insertado correctamente en el curso.");
            }
            // Si el alumno existe debemos comprobar si está en el curso correcto
            else{
                // Comprobamos si el alumno está en el curso adecuado, sino sale del proceso
                int cursoActual = cursoDAOImpl.findByAlumno(Long.valueOf(codigo));

                if (cursoActual != curso.getCodigo()) {
                    System.out.println("El alumno existe en otro curso distinto.");
                } else {
                    alumno.setNombre(nombre);
                    alumno.setApellidos(apellidos);
                    alumno.setPoblacion(poblacion);
                    alumno.setFechaNacimiento(fechaNacimiento);
                    alumnoDAOImpl.update(alumno);
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
            alumno = alumnoDAOImpl.findById(Long.valueOf(codigo));
            // Comprobamos si el alumno está en el curso adecuado, sino sale del proceso
            int cursoActual = cursoDAOImpl.findByAlumno(Long.valueOf(codigo));
            if (cursoActual != curso.getCodigo()) {
                System.out.println("El alumno existe en otro curso distinto.");
            } else {
                // Eliminamos los examenes del alumno.
                examenDAOImpl.deleteByAlumnoId(Long.valueOf(codigo));

                // Eliminamos al alumno de las asignaturas
                alumnoDAOImpl.deleteRelacionesById(Long.valueOf(codigo));
                alumnoDAOImpl.deleteAsignaturas(Long.valueOf(codigo));
                alumnoDAOImpl.deleteById(Long.valueOf(codigo));
                System.out.println("Alumno borrado correctamente.");
            }
        }
    }

    /** Listar cursos **/
    private static void listarCursos(){
        System.out.println("============================================================");
        System.out.println("Listado de cursos.");
        System.out.println("============================================================");

        List<Curso> cursos = cursoDAOImpl.getAll();
        for(Curso curso: cursos){
            System.out.println(curso.toString());
        }
    }

    /** Listar asignaturas de un curso **/
    private static void listarAsignaturasCurso() {
        int codigoCurso = InputUtils.readInt(sc, "Código del curso: ");

        Curso curso = buscarCurso(codigoCurso);
        if (curso == null) {
            System.out.println("Curso no encontrado.");
        } else {
            List<Asignatura> asignaturas = asignaturaDAOImpl.getAllByCurso(curso);
            if(!asignaturas.isEmpty()) {
                System.out.println("============================================================");
                System.out.println("Listado de asignaturas del curso " + curso.getDescripcion());
                System.out.println("============================================================");
                for(Asignatura asignatura: asignaturas){
                    asignatura.mostrarDatos();
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
            if(!profesorDAOImpl.getAllByCurso(curso).isEmpty()) {
                // Mostramos la lista de profesores
                List <Profesor> profesores = profesorDAOImpl.getAllByCurso(curso);
                System.out.println("============================================================");
                System.out.println("Lista de profesores del curso.");
                System.out.println("============================================================");
                for (Profesor profesor: profesores){
                    profesor.mostrarDatos();
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
            if(alumnoDAOImpl.getAllByCurso(curso).size()>0){
                System.out.println("============================================================");
                System.out.println("Listado de alumnos del curso " + curso.getDescripcion());
                System.out.println("============================================================");
                List <Alumno> alumnos = alumnoDAOImpl.getAllByCurso(curso);
                for (Alumno alumno: alumnos){
                    alumno.mostrarDatos();
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
            if (!profesorDAOImpl.getAllByCurso(curso).isEmpty()) {
                // Seleccionamos un profesor que tenga menos de 2 asignaturas asignadas
                profesor = seleccionarProfesor(curso);
                // Recorremos las asignaturas
                List<Asignatura> asignaturas =  asignaturaDAOImpl.getAllByProfe(profesor);
                for(Asignatura asignatura: asignaturas){

                    List<Alumno> listaAlumnos = alumnoDAOImpl.getAllByAsignatura(asignatura);
                    if(listaAlumnos.size()<=0){
                        System.out.println("No hay alumnos para la asignatura " + asignatura.getNombre());
                    }else{
                        System.out.println("============================================================");
                        System.out.println("Lista de alumnos de la asignatura " + asignatura.getNombre());
                        System.out.println("============================================================");
                        for(Alumno alumno: listaAlumnos){
                            alumno.mostrarDatos();
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
            int cursoActual = cursoDAOImpl.findByAlumno(Long.valueOf(alumno.getCodigo()));
            if (curso.getCodigo() != cursoActual) {
                System.out.println("El alumno existe en otro curso distinto.");
            } else {
                List<Examen> examenes = examenDAOImpl.getAllByCodAlumno(alumno.getCodigo());
                if(examenes.size()>0) {
                    // Mostramos la lista de examenes y notas
                    System.out.println("============================================================================");
                    System.out.println("Lista de exámenes y notas del alumno " + alumno.getNombre() + " " + alumno.getApellidos());
                    System.out.println("============================================================================");
                    // Ordenamos la lista por fecha
                    Collections.sort(examenes);
                    for (Examen examen : examenes) {
                        System.out.println(examen.toString());
                    }
                }else{
                    System.out.println("El alumno no tiene exámenes.");
                }
            }
        }
    }
}