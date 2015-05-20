package gt.com.kinal.juanlopez.peliculas;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;

import java.util.ArrayList;

import gt.com.kinal.juanlopez.peliculas.BaseDatos.BDD_sqlite;
import gt.com.kinal.juanlopez.peliculas.beans.Pelicula;
import gt.com.kinal.juanlopez.peliculas.beans.Usuario;


public class MainActivity extends ActionBarActivity {

    public String user;

    private Resources re;

    private Toolbar mToolbar;
    private TabHost tabHost;

    public SQLiteDatabase db;
    public BDD_sqlite sqlite;

    public ListView lista;
    public ListView lista1;
    public PeliculasAdapter adapter;

    ArrayList<Pelicula> listaPelicula = new ArrayList<Pelicula>();
    ArrayList<Pelicula> listBackupDatas = new ArrayList<Pelicula>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = "";
        setupDrawer();
        Login();
        llenarLista();
    }

    private void setupDrawer() {
        re = getResources();

        lista = (ListView)findViewById(R.id.listView);
        lista1 = (ListView)findViewById(R.id.listView2);

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);

        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("miTab");
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator("Destacados", re.getDrawable(android.R.drawable.ic_btn_speak_now));

        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("miTab2");
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("Favoritos", re.getDrawable(android.R.drawable.ic_dialog_email));

        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);
    }

    public void llenarLista(){
        sqlite = new BDD_sqlite(getBaseContext());
        db = sqlite.getReadableDatabase();

        String Sql = "SELECT * FROM USUARIOS";

        Cursor cc = db.rawQuery(Sql, null);
        Pelicula obj;

        if (cc.moveToFirst()) {
            do {

                obj = new Pelicula();
                obj.setImg(R.drawable.ic_launcher);
                obj.setTitulo("Bob Sponja");
                obj.setDescripcion("Bob Sponja");

                listaPelicula.add(obj);
                listBackupDatas.add(obj);

            } while (cc.moveToNext());
        }
        adapter = new PeliculasAdapter();
        lista.setAdapter(adapter);
        lista.setTextFilterEnabled(true);
        lista1.setAdapter(adapter);
        lista1.setTextFilterEnabled(true);
        adapter.notifyDataSetChanged();
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
        if (valida <= 0) {
            Intent intent = new Intent(MainActivity.this, clsLogin.class);
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

                String sql1 = "UPDATE USUARIOS SET ESTADO='0' WHERE USUARIO='" + user + "'";
                db.execSQL(sql1);
                db.close();

                Intent intent = new Intent(MainActivity.this, clsLogin.class);
                startActivity(intent);
                finish();

            } catch (SQLException e) {
                db.close();
                String message = e.toString();
                Toast.makeText(this, "0)" + message, Toast.LENGTH_LONG).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class PeliculasAdapter extends BaseAdapter implements
            Filterable {
        private PeliculaFilter peliculaFilter;

        @Override
        public int getCount() {
            return listaPelicula.size();
        }

        @Override
        public Filter getFilter() {
            if (peliculaFilter == null)
                peliculaFilter = new PeliculaFilter();
            return peliculaFilter;
        }

        @Override
        public Pelicula getItem(int position) {
            return listaPelicula.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            PlayerViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(
                        getApplicationContext()).inflate(
                        R.layout.frm_lista, null);
                holder = new PlayerViewHolder();

                holder.Titulo = (TextView) convertView.findViewById(R.id.txtTitulo);
                holder.Descript = (TextView) convertView.findViewById(R.id.txtDescrip);
                holder.thumb = (ImageView) convertView.findViewById(R.id.imgIcon);

                convertView.setTag(holder);
            } else {
                holder = (PlayerViewHolder) convertView.getTag();
            }
            holder.Titulo.setTextColor(R.color.secondary_text);
            holder.Titulo.setText(getItem(position).Titulo);
            holder.Descript.setText(getItem(position).Descripcion);
            holder.Descript.setTextColor(R.color.secondary_text);
            holder.thumb.setImageResource(getItem(position).getImg());
            return convertView;
        }

    }

    private class PeliculaFilter extends Filter {
        @SuppressLint("DefaultLocale")
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // 	We implement here the filter logic
            ArrayList<Pelicula> filters = new ArrayList<Pelicula>();
            if (constraint == null || constraint.length() == 0) {
                // 	No filter implemented we return all the list
                for (Pelicula player : listBackupDatas) {
                    filters.add(player);
                }
                results.values = filters;
                results.count = filters.size();
            } else {
                //We perform filtering operation
                for (Pelicula row : listBackupDatas) {
                    if (row.Titulo.toUpperCase().startsWith(
                            constraint.toString().toUpperCase())) {
                        filters.add(row);
                    }
                }
                results.values = filters;
                results.count = filters.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            if (results.count == 0) {
                listaPelicula.clear();
                adapter.notifyDataSetInvalidated();
            } else {
                listaPelicula.clear();
                @SuppressWarnings("unchecked")
                ArrayList<Pelicula> resultList = (ArrayList<Pelicula>) results.values;
                for (Pelicula row : resultList) {
                    listaPelicula.add(row);
                    adapter.notifyDataSetChanged();
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    private static class PlayerViewHolder {
        public ImageView thumb;
        public TextView Titulo;
        public TextView Descript;
    }

}

