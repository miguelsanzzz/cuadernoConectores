package com.academiadb4o.database;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

public final class ConexionDb4o {
    private static final ConexionDb4o INSTANCE = new ConexionDb4o();
    private final ObjectContainer db;

    private ConexionDb4o() { db = Db4oEmbedded.openFile("academia.yap"); }

    public static ConexionDb4o getInstance() { return INSTANCE; }

    public ObjectContainer getDb() { return db; }

    public void close() { db.close(); }
}