package com.tienda.model;

import java.time.LocalDate;

public class Venta {
    private final int id, idCliente, idVideojuego, unidades;
    private final LocalDate fecha;
    private final double importe;

    public Venta(int id, int idCliente, int idVideojuego, LocalDate fecha, int unidades, double importe) {
        this.id = id; this.idCliente = idCliente; this.idVideojuego = idVideojuego;
        this.fecha = fecha; this.unidades = unidades; this.importe = importe;
    }

    public int getId() { return id; }
    public int getIdCliente() { return idCliente; }
    public int getIdVideojuego() { return idVideojuego; }
    public LocalDate getFecha() { return fecha; }
    public int getUnidades() { return unidades; }
    public double getImporte() { return importe; }

    @Override
    public String toString() {
        return id + " | cliente " + idCliente + " | juego " + idVideojuego + " | " + fecha
                + " | " + unidades + " uds | " + importe + " €";
    }
}