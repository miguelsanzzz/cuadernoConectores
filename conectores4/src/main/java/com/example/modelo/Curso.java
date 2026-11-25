package com.example.modelo;


import java.util.ArrayList;
import java.util.List;

public class Curso {

    private int codigo;
    private String nombre;
    private List<Asignatura> asignaturas = new ArrayList<>();

    public Curso() {}

    public Curso(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public int getCodigo() { return codigo; }
    public String getNombre() { return nombre; }

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre;
    }
}

