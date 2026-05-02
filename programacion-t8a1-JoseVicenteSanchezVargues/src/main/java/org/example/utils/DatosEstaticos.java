package org.example.utils;

import org.example.modelo.*;
import org.example.main.GestionEscolar;

import java.time.LocalDate;

public class DatosEstaticos {
    static{
        /*
        Datos predefinidos y preparados con anterioridad.
        Han sido revisados para que cumplan con las restricciones del sistema.
         */

        /* 
        Obtenemos el objeto estático para poder trabajar con la lista de cursos
        y a partir de aquí ir creciendo la aplicación con datos. 
         */

        /*// Alta de cursos
        Curso c1 = new Curso(1,"1º DAM");
        Curso c2 = new Curso(2,"2º DAM");
        GestionEscolar.getCursos().add(c1);
        GestionEscolar.getCursos().add(c2);

        // Alta de profesores
        Profesor p1 = new Profesor(1,"Carlos","García López",
                "Valencia",LocalDate.of(1980,5,12),
                "123456789","Interino");
        Profesor p2 = new Profesor(2,"María","Sánchez Ruiz",
                "Alicante",LocalDate.of(1975,11,3),
                "987654321","Funcionario en prácticas");
        Profesor p3 = new Profesor(3,"Javier","Martínez Torres",
                "Castellón",LocalDate.of(1990,2,28),
                "666777888","Funcionario de carrera");
        Profesor p4 = new Profesor(4,"Lucía","Fernández Gómez",
                "Elche",LocalDate.of(1988,7,19),
                "666222999","Catedrático");
        Profesor p5 = new Profesor(5,"Ana","Soler Montés",
                "Torrent",LocalDate.of(1987,9,21),
                "666333444","Comisión de servicios");
        GestionEscolar.getCursos().get(0).asignarPersona(p1);
        GestionEscolar.getCursos().get(0).asignarPersona(p2);
        GestionEscolar.getCursos().get(1).asignarPersona(p3);
        GestionEscolar.getCursos().get(1).asignarPersona(p4);
        GestionEscolar.getCursos().get(1).asignarPersona(p5);

        // Alta de asignaturas
        Asignatura a1 = new Asignatura(1,"Matemáticas");
        a1.impartir(p1);
        c1.asignarAsignatura(a1);
        Asignatura a2 = new Asignatura(2,"Historia");
        a2.impartir(p2);
        c1.asignarAsignatura(a2);
        Asignatura a3 = new Asignatura(3,"Lengua Castellana");
        a3.impartir(p3);
        c2.asignarAsignatura(a3);
        Asignatura a4 = new Asignatura(4,"Programación");
        a4.impartir(p3);
        c2.asignarAsignatura(a4);
        Asignatura a5 = new Asignatura(5,"Biología");
        a5.impartir(p4);
        c2.asignarAsignatura(a5);
        Asignatura a6 = new Asignatura(6,"Física");
        a6.impartir(p4);
        c2.asignarAsignatura(a6);
        // Asignatura sin que nadie la imparta
        Asignatura a7 = new Asignatura(7,"Educación Física");
        c2.asignarAsignatura(a7);

        // Alta de alumnos
        Alumno al1  = new Alumno(1,"Álvaro","Serrano López",
                "Valencia",LocalDate.of(2005,3,12));
        GestionEscolar.getCursos().get(0).asignarPersona(al1);
        al1.matricular(a1);

        Alumno al2  = new Alumno(2, "María","Gómez Ferrer",
                "Torrent",LocalDate.of(2004,11,5));
        GestionEscolar.getCursos().get(0).asignarPersona(al2);
        al2.matricular(a1);

        Alumno al3  = new Alumno(3, "Javier","Pérez Torres",
                "Paterna",LocalDate.of(2006,1,22));
        GestionEscolar.getCursos().get(0).asignarPersona(al3);
        al3.matricular(a1);
        al3.matricular(a2);

        Alumno al4  = new Alumno(4, "Lucía","Martínez Rivera",
                "Gandía", LocalDate.of(2005,7,14));
        GestionEscolar.getCursos().get(0).asignarPersona(al4);
        al4.matricular(a1);
        al4.matricular(a2);

        Alumno al5  = new Alumno(5, "Hugo", "López García",
                "Sagunto",LocalDate.of(2004,10,3));
        GestionEscolar.getCursos().get(0).asignarPersona(al5);
        al5.matricular(a2);

        Alumno al6  = new Alumno(6, "Daniela",  "Sánchez Ortega",
                "Burjassot",  LocalDate.of(2005,5,19));
        GestionEscolar.getCursos().get(0).asignarPersona(al6);
        al6.matricular(a2);

        Alumno al7  = new Alumno(7, "Pablo","Ramírez Vidal",
                "Valencia",LocalDate.of(2006,2,9));
        GestionEscolar.getCursos().get(0).asignarPersona(al7);
        al7.matricular(a1);
        al7.matricular(a2);

        Alumno al8  = new Alumno(8, "Sofía","Navarro Soler",
                "Alaquàs",LocalDate.of(2004,8,25));
        GestionEscolar.getCursos().get(0).asignarPersona(al8);
        al8.matricular(a1);
        al8.matricular(a2);

        Alumno al9  = new Alumno(9, "Diego","Domínguez Mora",
                "Manises",LocalDate.of(2005,12,1));
        GestionEscolar.getCursos().get(0).asignarPersona(al9);
        al9.matricular(a1);

        Alumno al10 = new Alumno(10,"Elena","Gil Sanchis",
                "Aldaia", LocalDate.of(2006,4,16));
        GestionEscolar.getCursos().get(0).asignarPersona(al10);
        al10.matricular(a1);

        Alumno al11 = new Alumno(11,"Carlos","Vila Montes",
                "Valencia",LocalDate.of(2005,9,7));
        GestionEscolar.getCursos().get(1).asignarPersona(al11);
        al11.matricular(a3);
        al11.matricular(a4);
        al11.matricular(a5);
        al11.matricular(a6);

        Alumno al12 = new Alumno(12,"Irene","Roca Albiol",
                "Xàtiva", LocalDate.of(2004,6,11));
        GestionEscolar.getCursos().get(1).asignarPersona(al12);
        al12.matricular(a3);
        al12.matricular(a4);
        al12.matricular(a5);
        al12.matricular(a6);

        Alumno al13 = new Alumno(13,"Marcos","Ibáñez Tomás",
                "Ontinyent",  LocalDate.of(2006,3,5));
        GestionEscolar.getCursos().get(1).asignarPersona(al13);
        al13.matricular(a3);
        al13.matricular(a5);

        Alumno al14 = new Alumno(14,"Claudia",  "Ruiz Pascual",
                "Cullera",LocalDate.of(2005,1,30));
        GestionEscolar.getCursos().get(1).asignarPersona(al14);
        al14.matricular(a4);
        al14.matricular(a6);

        Alumno al15 = new Alumno(15,"Gonzalo",  "Castelló Revert",
                "Picassent",  LocalDate.of(2004,9,18));
        GestionEscolar.getCursos().get(1).asignarPersona(al15);
        al15.matricular(a3);
        al15.matricular(a4);
        al15.matricular(a5);
        al15.matricular(a6);

        Alumno al16 = new Alumno(16,"Nerea","Vázquez Costa",
                "Sueca",  LocalDate.of(2006,11,13));
        GestionEscolar.getCursos().get(1).asignarPersona(al16);
        al16.matricular(a3);

        Alumno al17 = new Alumno(17,"Alejandro","Tárrega Blasco",
                "Carcaixent", LocalDate.of(2005,7,28));
        GestionEscolar.getCursos().get(1).asignarPersona(al17);
        al17.matricular(a3);
        al17.matricular(a4);

        Alumno al18 = new Alumno(18,"Paula","Benavent Beltrán",
                "Valencia",LocalDate.of(2005,10,10));
        GestionEscolar.getCursos().get(1).asignarPersona(al18);
        al18.matricular(a5);
        al18.matricular(a6);

        Alumno al19 = new Alumno(19,"Rafael","Giménez Lloret",
                "Mislata",LocalDate.of(2004,12,21));
        GestionEscolar.getCursos().get(1).asignarPersona(al19);
        al19.matricular(a3);
        al19.matricular(a4);
        al19.matricular(a5);
        al19.matricular(a6);

        Alumno al20 = new Alumno(20,"Laura","Fernández Roca",
                "Alcàsser",LocalDate.of(2006,6,2));
        GestionEscolar.getCursos().get(1).asignarPersona(al20);
        al20.matricular(a3);
        al20.matricular(a4);
        al20.matricular(a5);
        al20.matricular(a6);

        // Creamos exámenes para las 6 asignaturas
        // === A1 ===
        a1.getExamenes().add(new Examen(1, al1,  a1, LocalDate.of(2025, 10, 25), 7.0f));
        a1.getExamenes().add(new Examen(2, al2,  a1, LocalDate.of(2025,10, 25), 6.5f));
        a1.getExamenes().add(new Examen(3, al3,  a1, LocalDate.of(2025,10,25), 8.0f));
        a1.getExamenes().add(new Examen(4, al4,  a1, LocalDate.of(2025,11, 3), 5.25f));
        a1.getExamenes().add(new Examen(5, al5,  a1, LocalDate.of(2025,11,3), 9.0f));

        // === A2 ===
        a2.getExamenes().add(new Examen(6, al6,  a2, LocalDate.of(2025, 10,28), 6.75f));
        a2.getExamenes().add(new Examen(7, al7,  a2, LocalDate.of(2025,10, 28), 7.25f));
        a2.getExamenes().add(new Examen(8, al8,  a2, LocalDate.of(2025,11,4), 4.5f));
        a2.getExamenes().add(new Examen(9, al9,  a2, LocalDate.of(2025,11, 4), 8.75f));
        a2.getExamenes().add(new Examen(10, al10, a2, LocalDate.of(2025,11,4), 6.0f));

        // === A3 ===
        a3.getExamenes().add(new Examen(11, al11, a3, LocalDate.of(2025, 10,30), 7.9f));
        a3.getExamenes().add(new Examen(12, al12, a3, LocalDate.of(2025,10, 30), 5.0f));
        a3.getExamenes().add(new Examen(13, al13, a3, LocalDate.of(2025,10,30), 6.2f));
        a3.getExamenes().add(new Examen(14, al14, a3, LocalDate.of(2025,11, 7), 9.5f));
        a3.getExamenes().add(new Examen(15, al15, a3, LocalDate.of(2025,11,7), 7.0f));

        // === A4 ===
        a4.getExamenes().add(new Examen(16, al16, a4, LocalDate.of(2025, 9,26), 8.4f));
        a4.getExamenes().add(new Examen(17, al17, a4, LocalDate.of(2025,9, 26), 6.8f));
        a4.getExamenes().add(new Examen(18, al18, a4, LocalDate.of(2025,9,26), 7.3f));
        a4.getExamenes().add(new Examen(19, al19, a4, LocalDate.of(2025,11,15), 5.9f));
        a4.getExamenes().add(new Examen(20, al20, a4, LocalDate.of(2025,11,15), 8.1f));

        // === A5 ===
        a5.getExamenes().add(new Examen(21, al1,  a5, LocalDate.of(2025, 9,27), 6.4f));
        a5.getExamenes().add(new Examen(22, al4,  a5, LocalDate.of(2025,9, 27), 7.6f));
        a5.getExamenes().add(new Examen(23, al7,  a5, LocalDate.of(2025,11,20), 8.9f));
        a5.getExamenes().add(new Examen(24, al10, a5, LocalDate.of(2025,11,20), 4.8f));
        a5.getExamenes().add(new Examen(25, al13, a5, LocalDate.of(2025,11,20), 9.2f));

        // === A6 ===
        a6.getExamenes().add(new Examen(26, al2,  a6, LocalDate.of(2025, 9,29), 5.7f));
        a6.getExamenes().add(new Examen(27, al5,  a6, LocalDate.of(2025,9, 29), 7.1f));
        a6.getExamenes().add(new Examen(28, al8,  a6, LocalDate.of(2025,9,29), 6.9f));
        a6.getExamenes().add(new Examen(29, al11, a6, LocalDate.of(2025,11,27), 8.6f));
        a6.getExamenes().add(new Examen(30, al14, a6, LocalDate.of(2025,11,27), 7.4f));*/
    }
}
