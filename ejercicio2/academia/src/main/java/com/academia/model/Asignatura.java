package com.academia.model;


public class Asignatura {
    private final int id, horasSemana, idProfesor, idCurso;
    private final String codigo, nombre;

    public Asignatura(int id, String codigo, String nombre, int horasSemana, int idProfesor, int idCurso) {
        this.id = id; this.codigo = codigo; this.nombre = nombre;
        this.horasSemana = horasSemana; this.idProfesor = idProfesor; this.idCurso = idCurso;
    }

    public int getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public int getHorasSemana() { return horasSemana; }
    public int getIdProfesor() { return idProfesor; }
    public int getIdCurso() { return idCurso; }

    @Override
    public String toString() {
        return id + " | " + codigo + " | " + nombre + " | " + horasSemana + "h | prof " + idProfesor + " | curso " + idCurso;
    }
}
