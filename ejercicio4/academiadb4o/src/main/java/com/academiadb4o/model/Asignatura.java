package com.academiadb4o.model;

public class Asignatura {
    private String codigo, nombre;
    private int horasSemana;
    private Profesor profesor;
    private Curso curso;

    public Asignatura() { }

    public Asignatura(String codigo, String nombre, int horasSemana, Profesor profesor, Curso curso) {
        this.codigo = codigo; this.nombre = nombre; this.horasSemana = horasSemana;
        this.profesor = profesor; this.curso = curso;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int getHorasSemana() { return horasSemana; }
    public void setHorasSemana(int horasSemana) { this.horasSemana = horasSemana; }
    public Profesor getProfesor() { return profesor; }
    public Curso getCurso() { return curso; }

    @Override
    public String toString() {
        return "Asignatura: " + codigo + " | " + nombre + " | " + horasSemana + "h | "
                + profesor.getNombre() + " | " + curso.getNombre();
    }
}