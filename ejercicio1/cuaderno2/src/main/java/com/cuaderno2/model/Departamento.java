package com.cuaderno2.model;

public class Departamento {
    private final int id;
    private final String nombre, ubicacion;

    public Departamento(int id, String nombre, String ubicacion) {
        this.id = id; this.nombre = nombre; this.ubicacion = ubicacion;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getUbicacion() { return ubicacion; }

    @Override
    public String toString() { return id + " | " + nombre + " | " + ubicacion; }
}