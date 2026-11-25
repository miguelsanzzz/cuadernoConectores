package com.example.model;

public class Videojuego {

    private int id;
    private String nombre;
    private String plataforma;
    private double precio;
    private int stock;

    public Videojuego() {
    }

    public Videojuego(int id, String nombre, String plataforma,
                      double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.plataforma = plataforma;
        this.precio = precio;
        this.stock = stock;
    }

    public Videojuego(String nombre, String plataforma,
                      double precio, int stock) {
        this(0, nombre, plataforma, precio, stock);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " | Nombre: " + nombre +
                " | Plataforma: " + plataforma +
                " | Precio: " + precio + " €" +
                " | Stock: " + stock;
    }
}
