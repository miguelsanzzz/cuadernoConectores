
-- EJERCICIO 1: MANEJO DE CONECTORES
-- BASE DE DATOS: act01

PRAGMA foreign_keys = ON;

-- Tabla DEPARTAMENTO
CREATE TABLE IF NOT EXISTS departamento (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    ubicacion TEXT
);

-- Tabla CLIENTES
CREATE TABLE IF NOT EXISTS clientes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    persona_contacto TEXT,
    tfno_contacto TEXT,
    CIF TEXT
);

-- Tabla PROYECTO
CREATE TABLE IF NOT EXISTS proyecto (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nombre TEXT NOT NULL,
    ubicacion TEXT,
    id_cliente INTEGER,
    FOREIGN KEY (id_cliente)
        REFERENCES clientes(id)
);

-- Tabla EMPLEADO
CREATE TABLE IF NOT EXISTS empleado (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_departamento INTEGER,
    id_proyecto INTEGER,
    nombre TEXT NOT NULL,
    apellidos TEXT,
    DNI TEXT,
    salario REAL,
    FOREIGN KEY (id_departamento)
        REFERENCES departamento(id),
    FOREIGN KEY (id_proyecto)
        REFERENCES proyecto(id)
);
