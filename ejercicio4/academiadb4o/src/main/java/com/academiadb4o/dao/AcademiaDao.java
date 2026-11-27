package com.academiadb4o.dao;

import com.academiadb4o.database.ConexionDb4o;
import com.academiadb4o.model.Alumno;
import com.academiadb4o.model.Asignatura;
import com.academiadb4o.model.Matricula;
import com.db4o.ObjectContainer;
import com.db4o.query.Predicate;
import com.db4o.query.Query;

import java.util.List;

public class AcademiaDao {
    private final ObjectContainer db = ConexionDb4o.getInstance().getDb();

    public void guardar(Object o) {
        db.store(o);
        db.commit();
    }

    public <T> List<T> listar(Class<T> clase) {
        return db.query(clase);
    }

    public <T> List<T> buscarPorEjemplo(T plantilla) {
        return db.queryByExample(plantilla);
    }

    public List<Asignatura> asignaturasConMasHoras(final int horas) {
        return db.query(new Predicate<Asignatura>() {
            @Override
            public boolean match(Asignatura a) {
                return a.getHorasSemana() > horas;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public List<Asignatura> asignaturasDeProfesor(String nombreProfesor) {
        Query q = db.query();
        q.constrain(Asignatura.class);
        q.descend("profesor").descend("nombre").constrain(nombreProfesor);
        return q.execute();
    }

    public void eliminar(Object o) {
        db.delete(o);
        db.commit();
    }

    @SuppressWarnings("unchecked")
    public void eliminarAlumno(Alumno a) {
        Query q = db.query();
        q.constrain(Matricula.class);
        q.descend("alumno").constrain(a).identity();
        List<Matricula> matriculas = q.execute();
        for (Matricula m : matriculas) db.delete(m);
        db.delete(a);
        db.commit();
    }
}