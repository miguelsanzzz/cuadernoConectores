package com.example.model;

public class Venta {

    private int id;
    private int clienteId;
    private int videojuegoId;
    private int cantidad;
    private double precioUnitario;
    private String fecha;

    public Venta() {
    }

    public Venta(int id, int clienteId, int videojuegoId,
                 int cantidad, double precioUnitario, String fecha) {
        this.id = id;
        this.clienteId = clienteId;
        this.videojuegoId = videojuegoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.fecha = fecha;
    }

    public Venta(int clienteId, int videojuegoId,
                 int cantidad, double precioUnitario) {
        this(0, clienteId, videojuegoId, cantidad, precioUnitario,
                java.time.LocalDateTime.now().toString());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getVideojuegoId() {
        return videojuegoId;
    }

    public void setVideojuegoId(int videojuegoId) {
        this.videojuegoId = videojuegoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "ID venta: " + id +
                " | Cliente: " + clienteId +
                " | Videojuego: " + videojuegoId +
                " | Cantidad: " + cantidad +
                " | Precio: " + precioUnitario + " €" +
                " | Fecha: " + fecha;
    }
}
