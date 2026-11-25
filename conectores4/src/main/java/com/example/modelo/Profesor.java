package com.example.modelo;


import java.util.ArrayList;
import java.util.List;

public class Profesor {

    private String dni;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String fechaNacimiento;
    private String codigoPostal;
    private String telefono;

    private List<Asignatura> asignaturas = new ArrayList<>();

    public Profesor() {}

    public Profesor(String dni, String nombre, String apellidos,
                    String direccion, String fechaNacimiento,
                    String codigoPostal, String telefono) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.codigoPostal = codigoPostal;
        this.telefono = telefono;
    }

    public String getDni() { return dni; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public List<Asignatura> getAsignaturas() { return asignaturas; }

    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

    @Override
    public String toString() {
        return dni + " - " + nombre + " " + apellidos;
    }
}

