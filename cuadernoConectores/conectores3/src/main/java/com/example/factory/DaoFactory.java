package com.example.factory;

import com.example.dao.IDao;
import com.example.dao.VideojuegoDaoImpl;
import com.example.dao.ClienteDaoImpl;
import com.example.dao.VentaDaoImpl;

import com.example.model.Videojuego;
import com.example.model.Cliente;
import com.example.model.Venta;

import java.sql.SQLException;

public class DaoFactory {

    private DaoFactory() {
    }

    public static IDao<Videojuego, Integer> getVideojuegoDao()
            throws SQLException {

        return new VideojuegoDaoImpl();
    }

    public static IDao<Cliente, Integer> getClienteDao()
            throws SQLException {

        return new ClienteDaoImpl();
    }

    public static IDao<Venta, Integer> getVentaDao()
            throws SQLException {

        return new VentaDaoImpl();
    }
}
