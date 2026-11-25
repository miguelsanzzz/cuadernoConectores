package com.tienda.app;

import com.tienda.dao.IDao;
import com.tienda.database.ConexionBD;
import com.tienda.factory.DaoFactory;
import com.tienda.model.*;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static IDao<Videojuego, Integer> videojuegos = DaoFactory.getVideojuegoDao();
    static IDao<Cliente, Integer> clientes = DaoFactory.getClienteDao();
    static IDao<Venta, Integer> ventas = DaoFactory.getVentaDao();

    public static void main(String[] args) {
        int op;
        do {
            System.out.println("\n========= TIENDA DE VIDEOJUEGOS =========");
            System.out.println("1. Gestión de videojuegos\n2. Gestión de clientes\n3. Gestión de ventas\n0. Salir");
            op = leerInt("Opción: ");
            switch (op) {
                case 1: menuVideojuegos(); break;
                case 2: menuClientes(); break;
                case 3: menuVentas(); break;
                case 0: System.out.println("Adiós"); break;
                default: System.out.println("Opción no válida");
            }
        } while (op != 0);
        ConexionBD.getInstance().closeConnection();
    }

    static void menuVideojuegos() {
        int op;
        do {
            System.out.println("\n------ GESTIÓN DE VIDEOJUEGOS ------");
            System.out.println("1. Listar videojuegos\n2. Buscar videojuego por ID\n3. Añadir videojuego\n4. Modificar videojuego\n5. Eliminar videojuego\n0. Volver");
            op = leerInt("Opción: ");
            switch (op) {
                case 1: videojuegos.readRecords().forEach(System.out::println); break;
                case 2: System.out.println(videojuegos.readRecord(leerInt("Id: "))); break;
                case 3: resultado(videojuegos.createRecord(pedirVideojuego())); break;
                case 4: {
                    int id = leerInt("Id a modificar: ");
                    resultado(videojuegos.updateRecord(pedirVideojuego(), id));
                    break;
                }
                case 5: resultado(videojuegos.deleteRecord(leerInt("Id a eliminar: "))); break;
                case 0: break;
                default: System.out.println("Opción no válida");
            }
        } while (op != 0);
    }

    static void menuClientes() {
        int op;
        do {
            System.out.println("\n------ GESTIÓN DE CLIENTES ------");
            System.out.println("1. Listar clientes\n2. Buscar cliente por ID\n3. Añadir cliente\n4. Modificar cliente\n5. Eliminar cliente\n0. Volver");
            op = leerInt("Opción: ");
            switch (op) {
                case 1: clientes.readRecords().forEach(System.out::println); break;
                case 2: System.out.println(clientes.readRecord(leerInt("Id: "))); break;
                case 3: resultado(clientes.createRecord(pedirCliente())); break;
                case 4: {
                    int id = leerInt("Id a modificar: ");
                    resultado(clientes.updateRecord(pedirCliente(), id));
                    break;
                }
                case 5: resultado(clientes.deleteRecord(leerInt("Id a eliminar: "))); break;
                case 0: break;
                default: System.out.println("Opción no válida");
            }
        } while (op != 0);
    }

    static void menuVentas() {
        int op;
        do {
            System.out.println("\n------ GESTIÓN DE VENTAS ------");
            System.out.println("1. Listar ventas\n2. Buscar venta por ID\n3. Registrar venta\n4. Modificar venta\n5. Eliminar venta\n0. Volver");
            op = leerInt("Opción: ");
            switch (op) {
                case 1: ventas.readRecords().forEach(System.out::println); break;
                case 2: System.out.println(ventas.readRecord(leerInt("Id: "))); break;
                case 3: {
                    int idCliente = leerInt("Id cliente: ");
                    int idJuego = leerInt("Id videojuego: ");
                    int uds = leerInt("Unidades: ");
                    resultado(ventas.createRecord(new Venta(0, idCliente, idJuego, LocalDate.now(), uds, 0)));
                    break;
                }
                case 4: {
                    int id = leerInt("Id de la venta: ");
                    int uds = leerInt("Nuevas unidades: ");
                    resultado(ventas.updateRecord(new Venta(0, 0, 0, LocalDate.now(), uds, 0), id));
                    break;
                }
                case 5: resultado(ventas.deleteRecord(leerInt("Id a eliminar: "))); break;
                case 0: break;
                default: System.out.println("Opción no válida");
            }
        } while (op != 0);
    }

    static Videojuego pedirVideojuego() {
        System.out.print("Título: "); String titulo = sc.nextLine();
        System.out.print("Plataforma: "); String plataforma = sc.nextLine();
        System.out.print("Precio: "); double precio = Double.parseDouble(sc.nextLine());
        int stock = leerInt("Stock: ");
        return new Videojuego(0, titulo, plataforma, precio, stock);
    }

    static Cliente pedirCliente() {
        System.out.print("Nombre: "); String nombre = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();
        return new Cliente(0, nombre, email);
    }

    static int leerInt(String texto) {
        System.out.print(texto);
        return Integer.parseInt(sc.nextLine());
    }

    static void resultado(boolean ok) {
        System.out.println(ok ? "Operación realizada" : "No se pudo realizar");
    }
}