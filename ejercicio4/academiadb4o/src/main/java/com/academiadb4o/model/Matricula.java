package com.academiadb4o.model;

public class Matricula {
    private Alumno alumno;
    private Asignatura asignatura;
    private double calificacion;

    public Matricula() { }

    public Matricula(Alumno alumno, Asignatura asignatura, double calificacion) {
        this.alumno = alumno; this.asignatura = asignatura; this.calificacion = calificacion;
    }

    public Alumno getAlumno() { return alumno; }
    public Asignatura getAsignatura() { return asignatura; }
    public double getCalificacion() { return calificacion; }
    public void setCalificacion(double calificacion) { this.calificacion = calificacion; }

    @Override
    public String toString() {
        return alumno.getNombre() + " en " + asignatura.getNombre() + " | nota: " + calificacion;
    }
}