package main.java.com.example.model;


public class Curso {
    private final int id;
    private final String codigo, nombre;

    public Curso(int id, String codigo, String nombre) {
        this.id = id; this.codigo = codigo; this.nombre = nombre;
    }

    public int getId() { return id; }
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }

    @Override
    public String toString() { return id + " | " + codigo + " | " + nombre; }
}
