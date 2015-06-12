package gt.com.kinal.juanlopez.peliculas;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TextView;

import gt.com.kinal.juanlopez.peliculas.Adapter.NavigationDrawerAdapter;
import gt.com.kinal.juanlopez.peliculas.BaseDatos.BDD_sqlite;
import gt.com.kinal.juanlopez.peliculas.beans.Pelicula;


public class clsDetalleP extends ActionBarActivity {

    public SQLiteDatabase db;
    public BDD_sqlite sqlite;

    private Toolbar mToolbar;

    String titulo;
    private TextView textTitulo,textDescrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cls_detalle_p);

        setupDrawer();

        textTitulo = (TextView) findViewById(R.id.txtTitulo);
        textDescrip = (TextView) findViewById(R.id.txtDescrip);

        Bundle bun = getIntent().getExtras();
        titulo = bun.getString("titulo");

        llenarLista(titulo);


    }
    private void setupDrawer() {
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void llenarLista(String titulo2) {

        sqlite = new BDD_sqlite(getBaseContext());
        db = sqlite.getReadableDatabase();

        String Sql = "SELECT * FROM PELICULA WHERE TITULO='"+titulo2+"'";

        Cursor cc = db.rawQuery(Sql, null);

        if (cc.moveToFirst()) {
            do {
                textTitulo.setText(cc.getString(0));
                textDescrip.setText(cc.getString(1));
            } while (cc.moveToNext());
        }
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cls_detalle, menu);
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
        if (id == R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
