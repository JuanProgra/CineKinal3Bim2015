package gt.com.kinal.juanlopez.peliculas.BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Godinez Miranda on 19/05/2015.
 */
public class BDD_sqlite extends SQLiteOpenHelper {

    private static CursorFactory factory = null;

    public BDD_sqlite(Context ctx)
    {
        super(ctx, "Peliculas", factory, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query = "CREATE TABLE USUARIOS(USUARIO TEXT,PASSWORD TEXT,ESTADO TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //Se elimina la version anterior de la tabla

        db.execSQL("DROP TABLE IF EXISTS USUARIOS");
        //Se crea la nueva version de la tabla
        onCreate(db);
    }
}