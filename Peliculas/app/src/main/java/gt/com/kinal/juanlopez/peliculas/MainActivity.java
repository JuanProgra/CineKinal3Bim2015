package gt.com.kinal.juanlopez.peliculas;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import gt.com.kinal.juanlopez.peliculas.Adapter.NavigationDrawerAdapter;
import gt.com.kinal.juanlopez.peliculas.BaseDatos.BDD_sqlite;
import gt.com.kinal.juanlopez.peliculas.beans.Pelicula;
import gt.com.kinal.juanlopez.peliculas.beans.Usuario;
import gt.com.kinal.juanlopez.peliculas.fragment.AjustesFragment;
import gt.com.kinal.juanlopez.peliculas.fragment.FavoritosFragment;
import gt.com.kinal.juanlopez.peliculas.fragment.PeliculaFragment;
import gt.com.kinal.juanlopez.peliculas.fragment.PerfilFragment;

public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, NavigationDrawerFragment.FragmentDrawerListener {

    public String user;

    private Resources re;

    private Bitmap loadedImage;

    private Toolbar mToolbar;
    private TabHost tabHost;

    public SQLiteDatabase db;
    public BDD_sqlite sqlite;

    public ListView lista;
    public ListView lista1;
    //public PeliculasAdapter adapter;

    public PeliculasAdapter2 adapter2;

    ArrayList<Pelicula> listaPelicula = new ArrayList<Pelicula>();
    ArrayList<Pelicula> listBackupDatas = new ArrayList<Pelicula>();
    ArrayList<Pelicula> listaPelicula2 = new ArrayList<Pelicula>();
    ArrayList<Pelicula> listBackupDatas2 = new ArrayList<Pelicula>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        user = "";
        setupDrawer();
        Login();
    }

    private void setupDrawer() {
        re = getResources();

        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer_fragmnet);

        drawerFragment.setUp((DrawerLayout) findViewById(R.id.drawer_layout), mToolbar, R.id.navigation_drawer_fragmnet);
        drawerFragment.setDrawerListener(this);
    }


    public void llenarLista2() {
        listaPelicula2.clear();
        listBackupDatas2.clear();

        sqlite = new BDD_sqlite(getBaseContext());
        db = sqlite.getReadableDatabase();

        String Sql = "SELECT * FROM PELICULA WHERE ESTADO='0'";

        Cursor cc = db.rawQuery(Sql, null);
        Pelicula obj;

        if (cc.moveToFirst()) {
            do {

                obj = new Pelicula();
                obj.setImg(R.drawable.ic_launcher);
                obj.setTitulo(cc.getString(0));
                obj.setDescripcion(cc.getString(1).substring(0, 30) + "...");

                listaPelicula2.add(obj);
                listBackupDatas2.add(obj);

            } while (cc.moveToNext());
        }
        adapter2 = new PeliculasAdapter2();
        lista1.setAdapter(adapter2);
        //adapter.notifyDataSetChanged();
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


    private class PeliculasAdapter2 extends BaseAdapter implements
            Filterable {
        private PeliculaFilter2 peliculaFilter2;

        @Override
        public int getCount() {
            return listaPelicula2.size();
        }

        @Override
        public Filter getFilter() {
            if (peliculaFilter2 == null)
                peliculaFilter2 = new PeliculaFilter2();
            return peliculaFilter2;
        }

        @Override
        public Pelicula getItem(int position) {
            return listaPelicula2.get(position);
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

    private class PeliculaFilter2 extends Filter {
        @SuppressLint("DefaultLocale")
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // 	We implement here the filter logic
            ArrayList<Pelicula> filters = new ArrayList<Pelicula>();
            if (constraint == null || constraint.length() == 0) {
                // 	No filter implemented we return all the list
                for (Pelicula player : listBackupDatas2) {
                    filters.add(player);
                }
                results.values = filters;
                results.count = filters.size();
            } else {
                //We perform filtering operation
                for (Pelicula row : listBackupDatas2) {
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
                listaPelicula2.clear();
                //adapter.notifyDataSetInvalidated();
            } else {
                listaPelicula2.clear();
                @SuppressWarnings("unchecked")
                ArrayList<Pelicula> resultList = (ArrayList<Pelicula>) results.values;
                for (Pelicula row : resultList) {
                    listaPelicula2.add(row);
                    //adapter.notifyDataSetChanged();
                }
                //adapter.notifyDataSetChanged();
            }
        }
    }

    private static class PlayerViewHolder {
        public ImageView thumb;
        public TextView Titulo;
        public TextView Descript;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);


        switch (position) {
            case 1:
                fragment = new PeliculaFragment();
                title = "Peliculas";
                break;
            case 2:
                fragment = new FavoritosFragment();
                title = "Favoritos";
                break;
            case 3:
                fragment = new AjustesFragment();
                title = "Ajustes";
                break;
            case 4:
                fragment = new PerfilFragment();
                title = "Perfil";
                break;
            default:
                break;
        }
        if (fragment != null) {
            ((RelativeLayout) findViewById(R.id.ContainerLayout)).removeAllViewsInLayout();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.ContainerLayout, fragment);
            fragmentTransaction.commit();
            getSupportActionBar().setTitle(title);
        }


    }
}

