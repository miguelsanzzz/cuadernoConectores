package com.tienda.model;

public class Cliente {
    private final int id;
    private final String nombre, email;

    public Cliente(int id, String nombre, String email) {
        this.id = id; this.nombre = nombre; this.email = email;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }

    @Override
    public String toString() { return id + " | " + nombre + " | " + email; }
}