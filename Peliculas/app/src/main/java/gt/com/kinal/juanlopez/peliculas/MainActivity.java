package gt.com.kinal.juanlopez.peliculas;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import gt.com.kinal.juanlopez.peliculas.BaseDatos.BDD_sqlite;
import gt.com.kinal.juanlopez.peliculas.beans.Usuario;


public class MainActivity extends ActionBarActivity {

    public String user;

    private Toolbar mToolbar;

    public SQLiteDatabase db;
    public BDD_sqlite sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = "";

        setupDrawer();
        Login();
    }

    private void setupDrawer() {
        mToolbar = (Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(mToolbar);
    }
    public void Login() {

        sqlite = new BDD_sqlite(getBaseContext());
        db = sqlite.getReadableDatabase();

        String Sql = "SELECT ESTADO,USUARIO FROM USUARIOS WHERE ESTADO='1'";

        Cursor cc = db.rawQuery(Sql, null);
        Usuario obj;
        int valida = 0;
        if (cc.moveToFirst()) {
            do {
                valida++;
                user = cc.getString(1);
            } while (cc.moveToNext());
        }
        db.close();
        if (valida <= 0){
            Intent intent = new Intent(MainActivity.this , clsLogin.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_log) {

            sqlite = new BDD_sqlite(getBaseContext());
            db = sqlite.getReadableDatabase();

            try {

                String sql1 = "UPDATE USUARIOS SET ESTADO='0' WHERE USUARIO='"+user+"'";
                db.execSQL(sql1);
                db.close();

                Intent intent = new Intent(MainActivity.this , clsLogin.class);
                startActivity(intent);
                finish();

            } catch (SQLException e) {
                db.close();
                String message = e.toString();
                Toast.makeText(this, "0)" +message, Toast.LENGTH_LONG).show();
            }

            Toast toast = Toast.makeText(getApplicationContext(),"Cerrar Secion", Toast.LENGTH_SHORT);
            toast.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

