package com.example.conexion;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

import java.io.File;

public class ConexionDb4o {

    private static final String RUTA = "data/academia.db4o";

    public static ObjectContainer conectar() {

        File carpeta = new File("data");

        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        return Db4oEmbedded.openFile(
            Db4oEmbedded.newConfiguration(),
            RUTA
        );
    }
}
