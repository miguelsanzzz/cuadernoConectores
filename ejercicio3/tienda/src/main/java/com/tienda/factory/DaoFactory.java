package com.tienda.factory;

import com.tienda.dao.*;
import com.tienda.model.*;

public class DaoFactory {
    private DaoFactory() { }

    public static IDao<Videojuego, Integer> getVideojuegoDao() { return new VideojuegoDaoImpl(); }
    public static IDao<Cliente, Integer> getClienteDao() { return new ClienteDaoImpl(); }
    public static IDao<Venta, Integer> getVentaDao() { return new VentaDaoImpl(); }
}