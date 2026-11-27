package com.cuaderno2.model;

public class Cliente {
    private final int id;
    private final String nombre, personaContacto, tfnoContacto, cif;

    public Cliente(int id, String nombre, String personaContacto, String tfnoContacto, String cif) {
        this.id = id; this.nombre = nombre; this.personaContacto = personaContacto;
        this.tfnoContacto = tfnoContacto; this.cif = cif;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getPersonaContacto() { return personaContacto; }
    public String getTfnoContacto() { return tfnoContacto; }
    public String getCif() { return cif; }

    @Override
    public String toString() {
        return id + " | " + nombre + " | " + personaContacto + " | " + tfnoContacto + " | " + cif;
    }
}