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
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;

import gt.com.kinal.juanlopez.peliculas.BaseDatos.BDD_sqlite;
import gt.com.kinal.juanlopez.peliculas.beans.Pelicula;
import gt.com.kinal.juanlopez.peliculas.beans.Usuario;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    public String user;

    private Resources re;

    private Bitmap loadedImage;

    private Toolbar mToolbar;
    private TabHost tabHost;

    public SQLiteDatabase db;
    public BDD_sqlite sqlite;

    public ListView lista;
    public ListView lista1;
    public PeliculasAdapter adapter;

    public PeliculasAdapter2 adapter2;

    ArrayList<Pelicula> listaPelicula = new ArrayList<Pelicula>();
    ArrayList<Pelicula> listBackupDatas = new ArrayList<Pelicula>();
    ArrayList<Pelicula> listaPelicula2 = new ArrayList<Pelicula>();
    ArrayList<Pelicula> listBackupDatas2 = new ArrayList<Pelicula>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (ListView) findViewById(R.id.listView);
        lista1 = (ListView) findViewById(R.id.listView2);

        llenarPeliculas();
        user = "";
        setupDrawer();
        Login();
        llenarLista();
        llenarLista2();

    }

    public void llenarPeliculas() {
        try {
            sqlite = new BDD_sqlite(getBaseContext());
            db = sqlite.getReadableDatabase();

            ContentValues peliculaConte = new ContentValues();

            String sql1 = "DELETE FROM PELICULA";
            db.execSQL(sql1);
            db.close();

            db = sqlite.getReadableDatabase();

            peliculaConte.put("TITULO", "Los Vengadores");
            peliculaConte.put("DESCRIPCION", "Cuando Tony Stark intenta reactivar un programa sin uso que tiene como objetivo de mantener la paz, las cosas comienzan a torcerse y los héroes más poderosos de la Tierra, incluyendo a Iron Man, Capitán América, Thor, El Increíble Hulk, Viuda Negra y Ojo de Halcón, se verán ante su prueba definitiva cuando el destino del planeta se ponga en juego. Cuando el villano Ultron emerge, le corresponderá a Los Vengadores detener sus terribles planes, que junto a incómodas alianzas llevarán a una inesperada acción que allanará el camino para una épica y única aventura.");
            peliculaConte.put("ESTADO", "1");

            db.insert("PELICULA", null, peliculaConte);
            db.close();

            db = sqlite.getReadableDatabase();

            peliculaConte.put("TITULO", "Clown: El Payaso del mal");
            peliculaConte.put("DESCRIPCION", "Un padre decide compra un traje de payaso para animar a su hijo en su sexto cumpleaños. Tras la fiesta se da cuenta de que es incapaz de quitárselo y su personalidad comienza a sufrir terroríficos cambios. Él y su familia deberán intentar quitárselo en una carrera contra el tiempo para terminar con la maldición, antes de que se complete la transformación y se convierta en un homicida con zapatos muy grandes.");
            peliculaConte.put("ESTADO", "1");

            db.insert("PELICULA", null, peliculaConte);
            db.close();

            db = sqlite.getReadableDatabase();

            peliculaConte.put("TITULO", "Tomorrowland: El Mundo del mañana");
            peliculaConte.put("DESCRIPCION", "Unidos por el mismo destino, un adolescente inteligente y optimista lleno de curiosidad científica y un antiguo niño prodigio inventor hastiado por las desilusiones se embarcan en una peligrosa misión para desenterrar los secretos de un enigmático lugar localizado en algún lugar del tiempo y el espacio conocido en la memoria colectiva como “Tomorrowland”");
            peliculaConte.put("ESTADO", "1");

            db.insert("PELICULA", null, peliculaConte);
            db.close();

            db = sqlite.getReadableDatabase();

            peliculaConte.put("TITULO", "Heroe de Centro Comercial 2");
            peliculaConte.put("DESCRIPCION", "Kevin James vuelve a interpretar a Paul Blart, el guardia de seguridad que en esta ocasión se dirige a Las Vegas para atender una Exposición sobre su ramo de trabajo y aprovechará para llevarse a su hija Maya (Raini Rodriguez) para pasar tiempo juntos antes de que ella se vaya a estudiar fuera. Mientras está en la convención, Paul sin darse cuenta descubre que se lleva a cabo un atraco, así que como buen héroe, deberá detener a los criminales.");
            peliculaConte.put("ESTADO", "1");

            db.insert("PELICULA", null, peliculaConte);
            db.close();
        } catch (SQLException e) {
            db.close();
            String message = e.toString();
            Toast.makeText(this, "0)" + message, Toast.LENGTH_LONG).show();
        }

    }

    private void setupDrawer() {
        re = getResources();

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

    public void llenarLista() {
        listaPelicula.clear();
        listBackupDatas.clear();

        sqlite = new BDD_sqlite(getBaseContext());
        db = sqlite.getReadableDatabase();

        String Sql = "SELECT * FROM PELICULA WHERE ESTADO='1'";

        Cursor cc = db.rawQuery(Sql, null);
        Pelicula obj;

        if (cc.moveToFirst()) {
            do {

                obj = new Pelicula();
                obj.setImg(R.drawable.ic_launcher);
                obj.setTitulo(cc.getString(0));
                obj.setDescripcion(cc.getString(1).substring(0, 30) + "...");

                listaPelicula.add(obj);
                listBackupDatas.add(obj);

            } while (cc.moveToNext());
        }
        adapter = new PeliculasAdapter();
        lista.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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

            holder.Titulo.setTag(position);


            holder.Titulo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos = (Integer) v.getTag();

                    String cat_ID   = listaPelicula.get(pos).getTitulo();

                    Aler(cat_ID);
                }
            });
            return convertView;

        }

    }
    public void Aler (String titulo) {

        final String ti = titulo;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(titulo);

        builder.setMessage(titulo);

        builder.setPositiveButton("Agregar favoritos", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                sqlite = new BDD_sqlite(getBaseContext());
                db = sqlite.getReadableDatabase();

                String sql1 = "UPDATE PELICULA SET ESTADO='0' WHERE TITULO='"+ti+"'";
                db.execSQL(sql1);
                db.close();

                llenarLista();
                llenarLista2();
            }
        });

        builder.setNegativeButton("Detalles", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent iMod = new Intent(MainActivity.this, clsDetalleP.class);
                iMod.putExtra("titulo", ti);
                startActivity(iMod);
                finish();
            }
        });
        builder.show();
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
                adapter.notifyDataSetInvalidated();
            } else {
                listaPelicula2.clear();
                @SuppressWarnings("unchecked")
                ArrayList<Pelicula> resultList = (ArrayList<Pelicula>) results.values;
                for (Pelicula row : resultList) {
                    listaPelicula2.add(row);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {

    }



}

