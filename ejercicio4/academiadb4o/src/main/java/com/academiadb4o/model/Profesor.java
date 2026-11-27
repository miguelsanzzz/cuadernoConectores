package com.academiadb4o.model;

public class Profesor {
    private String dni, nombre, apellidos;

    public Profesor() { }

    public Profesor(String dni, String nombre, String apellidos) {
        this.dni = dni; this.nombre = nombre; this.apellidos = apellidos;
    }

    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellidos() { return apellidos; }

    @Override
    public String toString() { return "Profesor: " + dni + " | " + nombre + " " + apellidos; }
}