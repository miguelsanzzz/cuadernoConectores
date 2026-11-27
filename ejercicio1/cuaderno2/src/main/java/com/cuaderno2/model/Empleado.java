package com.cuaderno2.model;

public class Empleado {
    private final int id, idDepartamento, idProyecto;
    private final String nombre, apellidos, dni;
    private final double salario;

    public Empleado(int id, int idDepartamento, int idProyecto, String nombre, String apellidos, String dni, double salario) {
        this.id = id; this.idDepartamento = idDepartamento; this.idProyecto = idProyecto;
        this.nombre = nombre; this.apellidos = apellidos; this.dni = dni; this.salario = salario;
    }

    public int getId() { return id; }
    public int getIdDepartamento() { return idDepartamento; }
    public int getIdProyecto() { return idProyecto; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getDni() { return dni; }
    public double getSalario() { return salario; }

    @Override
    public String toString() {
        return id + " | " + nombre + " " + apellidos + " | " + dni + " | " + salario + " € | dpto " + idDepartamento + " | proyecto " + idProyecto;
    }
}