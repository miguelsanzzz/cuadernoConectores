
package com.example;

import java.util.Scanner;

public class Main {

    private static final Scanner teclado = new Scanner(System.in);

    // Crear los DAO de las cuatro tablas

    private static final CrudDAO departamento = new CrudDAO(
            "departamento",
            new String[]{"nombre", "ubicacion"}
    );

    private static final CrudDAO empleado = new CrudDAO(
            "empleado",
            new String[]{"id_departamento", "id_proyecto",
                    "nombre", "apellidos", "DNI", "salario"}
    );

    private static final CrudDAO clientes = new CrudDAO(
            "clientes",
            new String[]{"nombre", "persona_contacto",
                    "tfno_contacto", "CIF"}
    );

    private static final CrudDAO proyecto = new CrudDAO(
            "proyecto",
            new String[]{"nombre", "ubicacion", "id_cliente"}
    );

    // Metodo principal
    public static void main(String[] args) {

        Database.inicializar();

        int opcion;

        do {

            System.out.println("\n================================");
            System.out.println("       GESTION DE EMPRESA");
            System.out.println("================================");
            System.out.println("1. Gestion de departamentos");
            System.out.println("2. Gestion de empleados");
            System.out.println("3. Gestion de clientes");
            System.out.println("4. Gestion de proyectos");
            System.out.println("0. Salir");

            opcion = leerEntero("Elige una opcion: ");

            switch (opcion) {

                case 1:
                    menuTabla("DEPARTAMENTOS", departamento);
                    break;

                case 2:
                    menuTabla("EMPLEADOS", empleado);
                    break;

                case 3:
                    menuTabla("CLIENTES", clientes);
                    break;

                case 4:
                    menuTabla("PROYECTOS", proyecto);
                    break;

                case 0:
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opcion incorrecta.");
            }

        } while (opcion != 0);

        teclado.close();
    }

    // Submenu de cada tabla
    public static void menuTabla(String nombre, CrudDAO dao) {

        int opcion;

        do {

            System.out.println("\n================================");
            System.out.println("       GESTION DE " + nombre);
            System.out.println("================================");
            System.out.println("1. Listar registros");
            System.out.println("2. Buscar por ID");
            System.out.println("3. Añadir registro");
            System.out.println("4. Modificar registro");
            System.out.println("5. Eliminar registro");
            System.out.println("0. Volver");

            opcion = leerEntero("Elige una opcion: ");

            switch (opcion) {

                case 1:
                    dao.listar();
                    break;

                case 2:
                    dao.buscar(leerEntero("Introduce el ID: "));
                    break;

                case 3:
                    dao.insertar(teclado);
                    break;

                case 4:
                    int idModificar =
                            leerEntero("ID del registro que quieres modificar: ");
                    dao.modificar(idModificar, teclado);
                    break;

                case 5:
                    int idEliminar =
                            leerEntero("ID del registro que quieres eliminar: ");
                    dao.eliminar(idEliminar);
                    break;

                case 0:
                    System.out.println("Volviendo al menu principal...");
                    break;

                default:
                    System.out.println("Opcion incorrecta.");
            }

        } while (opcion != 0);
    }

    // Leer numeros enteros y controlar errores
    private static int leerEntero(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            try {
                return Integer.parseInt(teclado.nextLine());

            } catch (NumberFormatException e) {
                System.out.println("Debes introducir un numero entero.");
            }
        }
    }
}
