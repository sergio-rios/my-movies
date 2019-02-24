package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import model.Pelicula;

public class DataBaseManagerPelicula extends DataBaseManager {

    private static final String NOMBRE_TABLA = "peliculas";

    private static final String ID = "pelicula_id";
    private static final String TITULO = "titulo";
    private static final String DIRECTOR = "director";
    private static final String PRODUCTORA = "productora";
    private static final String DURACION = "duracion";
    private static final String ANIO = "anio";
    private static final String VALORACION = "valoracion";
    private static final String SINOPSIS = "sinopsis";
    private static final String VISTA = "vista";
    private static final String IMG = "imagen_url";

    public static final String CREAR_TABLA = "CREATE TABLE " + NOMBRE_TABLA + " ("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TITULO + " TEXT NOT NULL, "
            + DIRECTOR + " TEXT NULL, "
            + PRODUCTORA + " TEXT NULL, "
            + DURACION + " INTEGER NULL, "
            + ANIO + " INTEGER NULL, "
            + VALORACION + " REAL NULL, "
            + SINOPSIS + " TEXT NULL, "
            + VISTA + " INTEGER NULL, "
            + IMG + " TEXT NULL"
            + ");";


    public DataBaseManagerPelicula(Context context) {
        super(context);
    }



    private ContentValues generarContentValues(String id, String titulo, String director, String productora, String duracion, String anio, String valoracion, String sinopsis, String vista, String imagen) {
        ContentValues valores = new ContentValues();
        valores.put(ID, id);
        valores.put(TITULO, titulo);
        valores.put(DIRECTOR, director);
        valores.put(PRODUCTORA, productora);
        valores.put(DURACION, duracion);
        valores.put(ANIO, anio);
        valores.put(VALORACION, valoracion);
        valores.put(SINOPSIS, sinopsis);
        valores.put(VISTA, vista);
        valores.put(IMG, imagen);

        return valores;
    }


    @Override
    public void cerrar() {
        super.getDb().close();
    }

    @Override
    public void insertar(String id, String titulo, String director, String productora, String duracion, String anio, String valoracion, String sinopsis, String vista, String imagen) {
        Log.d("insert", super.getDb().insert(NOMBRE_TABLA, null, generarContentValues(id, titulo, director, productora, duracion, anio, valoracion, sinopsis, vista, imagen))+"");
    }

    @Override
    public void actualizar(String id, String titulo, String director, String productora, String duracion, String anio, String valoracion, String sinopsis, String vista, String imagen) {
        ContentValues valores = new ContentValues();
        valores.put(ID, id);
        valores.put(TITULO, titulo);
        valores.put(DIRECTOR, director);
        valores.put(PRODUCTORA, productora);
        valores.put(DURACION, duracion);
        valores.put(ANIO, anio);
        valores.put(VALORACION, valoracion);
        valores.put(SINOPSIS, sinopsis);
        valores.put(VISTA, vista);
        valores.put(IMG, imagen);

        String [] args = new String[]{id};

        Log.d("update", super.getDb().update(NOMBRE_TABLA, valores, ID +"=?", args)+"");
    }

    @Override
    public void eliminar(String id) {
        super.getDb().delete(NOMBRE_TABLA, ID +"=?", new String[]{id});
    }

    @Override
    public void eliminarTodo() {
        super.getDb().execSQL("DELETE FROM " + NOMBRE_TABLA + ";");
    }

    @Override
    public Cursor cargarCursor(String order) {
        String [] columnas = new String[] {ID, TITULO, DIRECTOR, PRODUCTORA, DURACION, ANIO, VALORACION, SINOPSIS, VISTA, IMG};

        return super.getDb().query(NOMBRE_TABLA, columnas, null, null, null, null, order);
    }

    @Override
    Boolean compruebaRegistro(String id) {
        boolean existe = false;

        Cursor resultSet = super.getDb().rawQuery("SELECT * FROM " + NOMBRE_TABLA + " WHERE " + ID + "=" + id, null);

        if (resultSet.getCount() > 0)
            existe = true;

        return existe;
    }

    public List<Pelicula> getListaPeliculas(String order) {
        List<Pelicula> listaPeliculas = new ArrayList<>();

        Cursor c = cargarCursor(order);

        while (c.moveToNext()) {
            Pelicula pelicula = new Pelicula();

            pelicula.setId(c.getString(0));
            pelicula.setTitulo(c.getString(1));
            pelicula.setDirector(c.getString(2));
            pelicula.setProductora(c.getString(3));
            pelicula.setDuracion(c.getString(4));
            pelicula.setAnio(c.getString(5));
            pelicula.setValoracion(c.getString(6));
            pelicula.setSinopsis(c.getString(7));
            pelicula.setVista(c.getString(8));
            pelicula.setImagen(c.getString(9));

            listaPeliculas.add(pelicula);
        }

        return listaPeliculas;
    }

    public int count() {
        List<Pelicula> peliculas = getListaPeliculas("titulo");

        return peliculas.size();
    }
}