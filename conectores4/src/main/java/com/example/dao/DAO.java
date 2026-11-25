package com.example.dao;


import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import java.util.List;

public class DAO<T> {

    private final ObjectContainer db;
    private final Class<T> tipo;

    public DAO(ObjectContainer db, Class<T> tipo) {
        this.db = db;
        this.tipo = tipo;
    }

    public void insertar(T objeto) {
        db.store(objeto);
    }

    public List<T> listar() {
        ObjectSet<T> resultado = db.query(tipo);
        return resultado;
    }

    public T buscar(T ejemplo) {
        ObjectSet<T> resultado = db.queryByExample(ejemplo);

        if (resultado.hasNext()) {
            return resultado.next();
        }

        return null;
    }

    public void modificar(T objeto) {
        db.store(objeto);
    }

    public void eliminar(T objeto) {
        db.delete(objeto);
    }
}

