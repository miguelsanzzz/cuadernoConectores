package main.java.com.example.model;

import java.time.LocalDate;

public class Profesor extends Persona {
    public Profesor(int id, String dni, String nombre, String apellidos, String direccion, LocalDate fechaNacimiento, String codigoPostal, String telefono) {
        super(id, dni, nombre, apellidos, direccion, fechaNacimiento, codigoPostal, telefono);
    }
}
