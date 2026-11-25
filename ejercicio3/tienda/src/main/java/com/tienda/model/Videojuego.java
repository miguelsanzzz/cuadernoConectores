package com.tienda.model;

public class Videojuego {
    private final int id, stock;
    private final String titulo, plataforma;
    private final double precio;

    public Videojuego(int id, String titulo, String plataforma, double precio, int stock) {
        this.id = id; this.titulo = titulo; this.plataforma = plataforma;
        this.precio = precio; this.stock = stock;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getPlataforma() { return plataforma; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }

    @Override
    public String toString() {
        return id + " | " + titulo + " | " + plataforma + " | " + precio + " € | stock: " + stock;
    }
}