package com.academiadb4o.model;

public class Alumno {
    private String dni, nombre, apellidos;

    public Alumno() { }

    public Alumno(String dni, String nombre, String apellidos) {
        this.dni = dni; this.nombre = nombre; this.apellidos = apellidos;
    }

    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellidos() { return apellidos; }

    @Override
    public String toString() { return "Alumno: " + dni + " | " + nombre + " " + apellidos; }
}