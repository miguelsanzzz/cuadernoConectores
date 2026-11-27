package com.cuaderno2.model;

public class Proyecto {
    private final int id, idCliente;
    private final String nombre, ubicacion;

    public Proyecto(int id, String nombre, String ubicacion, int idCliente) {
        this.id = id; this.nombre = nombre; this.ubicacion = ubicacion; this.idCliente = idCliente;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getUbicacion() { return ubicacion; }
    public int getIdCliente() { return idCliente; }

    @Override
    public String toString() { return id + " | " + nombre + " | " + ubicacion + " | cliente " + idCliente; }
}