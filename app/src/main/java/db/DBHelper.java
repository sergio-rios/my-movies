package db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String NOMBRE_DB = "mispeliculas";
    private static int DB_SCHEM_VERSION = 1;

    public DBHelper(Context context) {
        super(context, NOMBRE_DB, null, DB_SCHEM_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DataBaseManagerPelicula.CREAR_TABLA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NOMBRE_DB);
        onCreate(sqLiteDatabase);
    }
}
