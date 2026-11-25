package com.academia.model;

import java.time.LocalDate;

public class Persona {
    private final int id;
    private final String dni, nombre, apellidos, direccion, codigoPostal, telefono;
    private final LocalDate fechaNacimiento;

    public Persona(int id, String dni, String nombre, String apellidos, String direccion, LocalDate fechaNacimiento, String codigoPostal, String telefono) {
        this.id = id; this.dni = dni; this.nombre = nombre; this.apellidos = apellidos;
        this.direccion = direccion; this.fechaNacimiento = fechaNacimiento;
        this.codigoPostal = codigoPostal; this.telefono = telefono;
    }

    public int getId() { return id; }
    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getDireccion() { return direccion; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getCodigoPostal() { return codigoPostal; }
    public String getTelefono() { return telefono; }

    @Override
    public String toString() {
        return id + " | " + dni + " | " + nombre + " " + apellidos + " | " + telefono;
    }
}