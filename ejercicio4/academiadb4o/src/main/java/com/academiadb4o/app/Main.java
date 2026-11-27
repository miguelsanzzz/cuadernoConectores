package com.academiadb4o.app;

import com.academiadb4o.dao.AcademiaDao;
import com.academiadb4o.database.ConexionDb4o;
import com.academiadb4o.model.*;

public class Main {
    public static void main(String[] args) {
        AcademiaDao dao = new AcademiaDao();

        // CREATE
        Profesor prof = new Profesor("11111111A", "Javier", "Castejón");
        Curso curso = new Curso("DAM", "Desarrollo de Aplicaciones Multiplataforma");
        Asignatura acd = new Asignatura("ACD", "Acceso a Datos", 5, prof, curso);
        Alumno ana = new Alumno("22222222B", "Ana", "López");
        dao.guardar(prof);
        dao.guardar(curso);
        dao.guardar(acd);
        dao.guardar(ana);
        dao.guardar(new Matricula(ana, acd, 8.5));

        // READ
        System.out.println("--- Asignaturas ---");
        dao.listar(Asignatura.class).forEach(System.out::println);
        System.out.println("--- Matrículas ---");
        dao.listar(Matricula.class).forEach(System.out::println);

        // QBE
        System.out.println("--- QBE: alumnos llamados Ana ---");
        dao.buscarPorEjemplo(new Alumno(null, "Ana", null)).forEach(System.out::println);

        // NQ
        System.out.println("--- NQ: asignaturas de más de 3 horas ---");
        dao.asignaturasConMasHoras(3).forEach(System.out::println);

        // SODA
        System.out.println("--- SODA: asignaturas del profesor Javier ---");
        dao.asignaturasDeProfesor("Javier").forEach(System.out::println);

        // UPDATE: primero se recupera el objeto, luego se modifica y se guarda
        Alumno recuperado = dao.buscarPorEjemplo(new Alumno(null, "Ana", null)).get(0);
        recuperado.setNombre("Ana María");
        dao.guardar(recuperado);
        System.out.println("Tras UPDATE: " + recuperado);

        // DELETE
        dao.eliminarAlumno(recuperado);
        System.out.println("Alumnos restantes: " + dao.listar(Alumno.class).size());
        System.out.println("Matrículas restantes: " + dao.listar(Matricula.class).size());

        ConexionDb4o.getInstance().close();
    }
}