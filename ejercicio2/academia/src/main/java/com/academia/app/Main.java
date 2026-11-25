package com.academia.app;

import com.academia.dao.*;
import com.academia.database.ConexionBD;
import com.academia.database.InicializadorBD;
import com.academia.model.*;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        InicializadorBD.crearTablas();

        CursoDao cursos = new CursoDao();
        ProfesorDao profesores = new ProfesorDao();
        AsignaturaDao asignaturas = new AsignaturaDao();
        AlumnoDao alumnos = new AlumnoDao();

        cursos.createRecord(new Curso(0, "DAM", "Desarrollo de Aplicaciones Multiplataforma"));
        profesores.createRecord(new Profesor(0, "11111111A", "Javier", "Castejón",
                "C/ Mayor 1", LocalDate.of(1980, 5, 10), "50001", "976000000"));
        int idCurso = cursos.readRecords().get(0).getId();
        int idProfesor = profesores.readRecords().get(0).getId();

        asignaturas.createRecord(new Asignatura(0, "ACD", "Acceso a Datos", 5, idProfesor, idCurso));
        alumnos.createRecord(new Alumno(0, "22222222B", "Ana", "López",
                "C/ Sol 2", LocalDate.of(2004, 3, 20), "50002", "600111222"));

        System.out.println("--- Asignaturas ---");
        asignaturas.readRecords().forEach(System.out::println);
        System.out.println("--- Alumnos ---");
        alumnos.readRecords().forEach(System.out::println);

        int idAlumno = alumnos.readRecords().get(0).getId();
        alumnos.updateRecord(new Alumno(idAlumno, "22222222B", "Ana María", "López",
                "C/ Sol 2", LocalDate.of(2004, 3, 20), "50002", "600111222"), idAlumno);
        System.out.println("Tras UPDATE: " + alumnos.readRecord(idAlumno));

        alumnos.deleteRecord(idAlumno);
        System.out.println("Tras DELETE: " + alumnos.readRecord(idAlumno));

        ConexionBD.getInstance().closeConnection();
    }
}