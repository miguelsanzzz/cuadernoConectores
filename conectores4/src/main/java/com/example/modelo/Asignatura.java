package com.example.modelo;

import java.util.ArrayList;
import java.util.List;

public class Asignatura {

    private int codigo;
    private String nombre;
    private int horasSemana;

    private Profesor profesor;
    private Curso curso;

    private List<Alumno> alumnos = new ArrayList<>();

    public Asignatura() {}

    public Asignatura(int codigo, String nombre, int horasSemana,
                      Profesor profesor, Curso curso) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.horasSemana = horasSemana;
        this.profesor = profesor;
        this.curso = curso;
    }

    public int getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public Profesor getProfesor() { return profesor; }
    public Curso getCurso() { return curso; }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }
}
