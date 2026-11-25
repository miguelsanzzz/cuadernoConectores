package com.example.app;

import com.example.dao.IDao;
import com.example.factory.DaoFactory;
import com.example.database.ConexionBD;

import com.example.model.Videojuego;
import com.example.model.Cliente;
import com.example.model.Venta;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);

    private static IDao<Videojuego, Integer> videojuegos;
    private static IDao<Cliente, Integer> clientes;
    private static IDao<Venta, Integer> ventas;

    public static void main(String[] args) {

        try {

            // Obtener los DAO mediante Factory.
            videojuegos = DaoFactory.getVideojuegoDao();
            clientes = DaoFactory.getClienteDao();
            ventas = DaoFactory.getVentaDao();

            int opcion;

            do {

                System.out.println("\n========= TIENDA DE VIDEOJUEGOS =========");
                System.out.println("1. Gestión de videojuegos");
                System.out.println("2. Gestión de clientes");
                System.out.println("3. Gestión de ventas");
                System.out.println("0. Salir");

                opcion = leerInt("Elige una opción: ");

                switch (opcion) {

                    case 1:
                        menuVideojuegos();
                        break;

                    case 2:
                        menuClientes();
                        break;

                    case 3:
                        menuVentas();
                        break;

                    case 0:
                        System.out.println("Saliendo de la aplicación...");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }

            } while (opcion != 0);

        } catch (Exception e) {

            System.out.println("Error: " + e.getMessage());

        } finally {

            ConexionBD.cerrarConexion();
            sc.close();
        }
    }

    // =====================================
    // MENÚ VIDEOJUEGOS
    // =====================================

    private static void menuVideojuegos() throws Exception {

        int opcion;

        do {

            System.out.println("\n------ GESTIÓN DE VIDEOJUEGOS ------");
            System.out.println("1. Listar videojuegos");
            System.out.println("2. Buscar videojuego por ID");
            System.out.println("3. Añadir videojuego");
            System.out.println("4. Modificar videojuego");
            System.out.println("5. Eliminar videojuego");
            System.out.println("0. Volver");

            opcion = leerInt("Elige una opción: ");

            switch (opcion) {

                case 1:
                    mostrarLista(videojuegos.findAll());
                    break;

                case 2:
                    buscarVideojuego();
                    break;

                case 3:
                    anadirVideojuego();
                    break;

                case 4:
                    modificarVideojuego();
                    break;

                case 5:
                    eliminar(videojuegos, leerInt("ID del videojuego: "));
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    private static void buscarVideojuego() throws Exception {

        int id = leerInt("Introduce el ID: ");

        Optional<Videojuego> resultado = videojuegos.findById(id);

        System.out.println(
                resultado.map(Object::toString)
                        .orElse("Videojuego no encontrado.")
        );
    }

    private static void anadirVideojuego() throws Exception {

        String nombre = leerTexto("Nombre: ");
        String plataforma = leerTexto("Plataforma: ");
        double precio = leerDouble("Precio: ");
        int stock = leerInt("Stock: ");

        if (precio < 0 || stock < 0) {
            System.out.println("Precio o stock no válidos.");
            return;
        }

        Videojuego v = new Videojuego(
                nombre, plataforma, precio, stock
        );

        if (videojuegos.insert(v)) {
            System.out.println("Videojuego añadido correctamente.");
        }
    }

    private static void modificarVideojuego() throws Exception {

        int id = leerInt("ID del videojuego: ");

        Optional<Videojuego> resultado = videojuegos.findById(id);

        if (resultado.isEmpty()) {
            System.out.println("Videojuego no encontrado.");
            return;
        }

        Videojuego v = resultado.get();

        v.setNombre(leerTexto("Nuevo nombre: "));
        v.setPlataforma(leerTexto("Nueva plataforma: "));
        v.setPrecio(leerDouble("Nuevo precio: "));
        v.setStock(leerInt("Nuevo stock: "));

        if (v.getPrecio() < 0 || v.getStock() < 0) {
            System.out.println("Valores no válidos.");
            return;
        }

        System.out.println(
                videojuegos.update(v)
                        ? "Videojuego modificado."
                        : "No se pudo modificar."
        );
    }

    // =====================================
    // MENÚ CLIENTES
    // =====================================

    private static void menuClientes() throws Exception {

        int opcion;

        do {

            System.out.println("\n------ GESTIÓN DE CLIENTES ------");
            System.out.println("1. Listar clientes");
            System.out.println("2. Buscar cliente por ID");
            System.out.println("3. Añadir cliente");
            System.out.println("4. Modificar cliente");
            System.out.println("5. Eliminar cliente");
            System.out.println("0. Volver");

            opcion = leerInt("Elige una opción: ");

            switch (opcion) {

                case 1:
                    mostrarLista(clientes.findAll());
                    break;

                case 2:
                    buscarCliente();
                    break;

                case 3:
                    anadirCliente();
                    break;

                case 4:
                    modificarCliente();
                    break;

                case 5:
                    eliminar(clientes, leerInt("ID del cliente: "));
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    private static void buscarCliente() throws Exception {

        int id = leerInt("Introduce el ID: ");

        Optional<Cliente> resultado = clientes.findById(id);

        System.out.println(
                resultado.map(Object::toString)
                        .orElse("Cliente no encontrado.")
        );
    }

    private static void anadirCliente() throws Exception {

        String nombre = leerTexto("Nombre: ");
        String email = leerTexto("Email: ");
        String telefono = leerTexto("Teléfono: ");

        Cliente c = new Cliente(nombre, email, telefono);

        System.out.println(
                clientes.insert(c)
                        ? "Cliente añadido correctamente."
                        : "No se pudo añadir el cliente."
        );
    }

    private static void modificarCliente() throws Exception {

        int id = leerInt("ID del cliente: ");

        Optional<Cliente> resultado = clientes.findById(id);

        if (resultado.isEmpty()) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        Cliente c = resultado.get();

        c.setNombre(leerTexto("Nuevo nombre: "));
        c.setEmail(leerTexto("Nuevo email: "));
        c.setTelefono(leerTexto("Nuevo teléfono: "));

        System.out.println(
                clientes.update(c)
                        ? "Cliente modificado correctamente."
                        : "No se pudo modificar."
        );
    }

    // =====================================
    // MENÚ VENTAS
    // =====================================

    private static void menuVentas() throws Exception {

        int opcion;

        do {

            System.out.println("\n------ GESTIÓN DE VENTAS ------");
            System.out.println("1. Listar ventas");
            System.out.println("2. Buscar venta por ID");
            System.out.println("3. Registrar venta");
            System.out.println("4. Modificar venta");
            System.out.println("5. Eliminar venta");
            System.out.println("0. Volver");

            opcion = leerInt("Elige una opción: ");

            switch (opcion) {

                case 1:
                    mostrarLista(ventas.findAll());
                    break;

                case 2:
                    buscarVenta();
                    break;

                case 3:
                    registrarVenta();
                    break;

                case 4:
                    modificarVenta();
                    break;

                case 5:
                    eliminar(ventas, leerInt("ID de la venta: "));
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opción no válida.");
            }

        } while (opcion != 0);
    }

    private static void buscarVenta() throws Exception {

        int id = leerInt("Introduce el ID de la venta: ");

        Optional<Venta> resultado = ventas.findById(id);

        System.out.println(
                resultado.map(Object::toString)
                        .orElse("Venta no encontrada.")
        );
    }

    private static void registrarVenta() throws Exception {

        System.out.println("\nCLIENTES DISPONIBLES:");

        mostrarLista(clientes.findAll());

        int clienteId = leerInt("ID del cliente: ");

        if (clientes.findById(clienteId).isEmpty()) {
            System.out.println("El cliente no existe.");
            return;
        }

        System.out.println("\nVIDEOJUEGOS DISPONIBLES:");

        mostrarLista(videojuegos.findAll());

        int videojuegoId = leerInt("ID del videojuego: ");

        Optional<Videojuego> juego =
                videojuegos.findById(videojuegoId);

        if (juego.isEmpty()) {
            System.out.println("El videojuego no existe.");
            return;
        }

        int cantidad = leerInt("Cantidad: ");

        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor que cero.");
            return;
        }

        Venta v = new Venta(
                clienteId,
                videojuegoId,
                cantidad,
                juego.get().getPrecio()
        );

        System.out.println(
                ventas.insert(v)
                        ? "Venta registrada y stock actualizado."
                        : "No se pudo registrar la venta."
        );
    }

    private static void modificarVenta() throws Exception {

        int id = leerInt("ID de la venta: ");

        Optional<Venta> resultado = ventas.findById(id);

        if (resultado.isEmpty()) {
            System.out.println("Venta no encontrada.");
            return;
        }

        System.out.println("\nCLIENTES DISPONIBLES:");
        mostrarLista(clientes.findAll());

        int clienteId = leerInt("Nuevo ID de cliente: ");

        if (clientes.findById(clienteId).isEmpty()) {
            System.out.println("El cliente no existe.");
            return;
        }

        System.out.println("\nVIDEOJUEGOS DISPONIBLES:");
        mostrarLista(videojuegos.findAll());

        int videojuegoId = leerInt("Nuevo ID de videojuego: ");

        Optional<Videojuego> juego =
                videojuegos.findById(videojuegoId);

        if (juego.isEmpty()) {
            System.out.println("El videojuego no existe.");
            return;
        }

        int cantidad = leerInt("Nueva cantidad: ");

        if (cantidad <= 0) {
            System.out.println("La cantidad debe ser mayor que cero.");
            return;
        }

        Venta v = new Venta(
                id,
                clienteId,
                videojuegoId,
                cantidad,
                juego.get().getPrecio(),
                java.time.LocalDateTime.now().toString()
        );

        System.out.println(
                ventas.update(v)
                        ? "Venta modificada y stock actualizado."
                        : "No se pudo modificar la venta."
        );
    }

    // =====================================
    // MÉTODOS GENERALES
    // =====================================

    private static <T, V> void eliminar(
            IDao<T, V> dao, V id) throws Exception {

        System.out.println(
                dao.delete(id)
                        ? "Registro eliminado correctamente."
                        : "No se encontró el registro."
        );
    }

    private static void mostrarLista(List<?> lista) {

        if (lista.isEmpty()) {
            System.out.println("No hay registros.");
        } else {
            for (Object elemento : lista) {
                System.out.println(elemento);
            }
        }
    }

    private static int leerInt(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            try {
                return Integer.parseInt(sc.nextLine().trim());

            } catch (NumberFormatException e) {
                System.out.println("Introduce un número entero válido.");
            }
        }
    }

    private static double leerDouble(String mensaje) {

        while (true) {

            System.out.print(mensaje);

            try {
                return Double.parseDouble(
                        sc.nextLine().trim().replace(',', '.')
                );

            } catch (NumberFormatException e) {
                System.out.println("Introduce un número válido.");
            }
        }
    }

    private static String leerTexto(String mensaje) {

        System.out.print(mensaje);

        return sc.nextLine().trim();
    }
}
