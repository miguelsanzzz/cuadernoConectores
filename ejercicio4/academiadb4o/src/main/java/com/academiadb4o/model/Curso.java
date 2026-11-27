package com.academiadb4o.model;

public class Curso {
    private String codigo, nombre;

    public Curso() { }

    public Curso(String codigo, String nombre) { this.codigo = codigo; this.nombre = nombre; }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }

    @Override
    public String toString() { return "Curso: " + codigo + " | " + nombre; }
}