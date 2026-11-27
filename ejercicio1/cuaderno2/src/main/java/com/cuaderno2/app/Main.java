package com.cuaderno2.app;

import com.cuaderno2.dao.*;
import com.cuaderno2.model.*;
import com.cuaderno2.utils.ConnectToDB;

import java.util.Scanner;
import java.util.function.Supplier;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        IDao<Departamento, Integer> departamentos = new DepartamentoDao();
        IDao<Empleado, Integer> empleados = new EmpleadoDao();
        IDao<Cliente, Integer> clientes = new ClienteDao();
        IDao<Proyecto, Integer> proyectos = new ProyectoDao();

        int op;
        do {
            System.out.println("\n========= GESTIÓN DE EMPRESA =========");
            System.out.println("1. Departamentos\n2. Empleados\n3. Clientes\n4. Proyectos\n0. Salir");
            op = leerInt("Opción: ");
            switch (op) {
                case 1: menuCrud("DEPARTAMENTOS", departamentos, Main::pedirDepartamento); break;
                case 2: menuCrud("EMPLEADOS", empleados, Main::pedirEmpleado); break;
                case 3: menuCrud("CLIENTES", clientes, Main::pedirCliente); break;
                case 4: menuCrud("PROYECTOS", proyectos, Main::pedirProyecto); break;
                case 0: System.out.println("Salir"); break;
                default: System.out.println("Opción no válida");
            }
        } while (op != 0);

        ConnectToDB.getInstance().closeConnection();
    }

    // Submenú CRUD común a las 4 tablas
    static <T> void menuCrud(String titulo, IDao<T, Integer> dao, Supplier<T> pedir) {
        int op;
        do {
            System.out.println("\n------ " + titulo + " ------");
            System.out.println("1. Listar\n2. Buscar por ID\n3. Añadir\n4. Modificar\n5. Eliminar\n0. Volver");
            op = leerInt("Opción: ");
            switch (op) {
                case 1:
                    dao.readRecords().forEach(System.out::println);
                    break;
                case 2: {
                    T t = dao.readRecord(leerInt("Id: "));
                    System.out.println(t != null ? t : "No existe");
                    break;
                }
                case 3:
                    resultado(dao.createRecord(pedir.get()));
                    break;
                case 4: {
                    int id = leerInt("Id a modificar: ");
                    resultado(dao.updateRecord(pedir.get(), id));
                    break;
                }
                case 5:
                    resultado(dao.deleteRecord(leerInt("Id a eliminar: ")));
                    break;
                case 0: break;
                default: System.out.println("Opción no válida");
            }
        } while (op != 0);
    }

    static Departamento pedirDepartamento() {
        String nombre = leerTexto("Nombre: ");
        String ubicacion = leerTexto("Ubicación: ");
        return new Departamento(0, nombre, ubicacion);
    }

    static Empleado pedirEmpleado() {
        int idDepartamento = leerInt("Id departamento: ");
        int idProyecto = leerInt("Id proyecto: ");
        String nombre = leerTexto("Nombre: ");
        String apellidos = leerTexto("Apellidos: ");
        String dni = leerTexto("DNI: ");
        double salario = leerDouble("Salario: ");
        return new Empleado(0, idDepartamento, idProyecto, nombre, apellidos, dni, salario);
    }

    static Cliente pedirCliente() {
        String nombre = leerTexto("Nombre: ");
        String contacto = leerTexto("Persona de contacto: ");
        String tfno = leerTexto("Teléfono: ");
        String cif = leerTexto("CIF: ");
        return new Cliente(0, nombre, contacto, tfno, cif);
    }

    static Proyecto pedirProyecto() {
        String nombre = leerTexto("Nombre: ");
        String ubicacion = leerTexto("Ubicación: ");
        int idCliente = leerInt("Id cliente: ");
        return new Proyecto(0, nombre, ubicacion, idCliente);
    }

    static String leerTexto(String texto) {
        System.out.print(texto);
        return sc.nextLine();
    }

    static int leerInt(String texto) {
        while (true) {
            System.out.print(texto);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Número no válido");
            }
        }
    }

    static double leerDouble(String texto) {
        while (true) {
            System.out.print(texto);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Número no válido");
            }
        }
    }

    static void resultado(boolean correcto) {
        if (correcto) {
            System.out.println("Operación realizada");
        } else {
            System.out.println("No se pudo realizar");
        }

    }
}