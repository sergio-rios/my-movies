package db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class DataBaseManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }


    public void cerrar() { db.close(); }

    abstract public void insertar(String id, String titulo, String director, String productora, String duracion, String anio, String valoracion, String sinopsis, String vista, String imagen);

    abstract public void actualizar(String id, String titulo, String director, String productora, String duracion, String anio, String valoracion, String sinopsis, String vista, String imagen);

    abstract public void eliminar(String id);

    abstract public void eliminarTodo();

    abstract public Cursor cargarCursor();

    abstract Boolean compruebaRegistro(String id);




    public DBHelper getHelper() {
        return helper;
    }

    public void setHelper(DBHelper helper) {
        this.helper = helper;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public void setDb(SQLiteDatabase db) {
        this.db = db;
    }
}

