package gt.com.kinal.juanlopez.peliculas.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.util.ArrayList;

import gt.com.kinal.juanlopez.peliculas.BaseDatos.BDD_sqlite;
import gt.com.kinal.juanlopez.peliculas.R;
import gt.com.kinal.juanlopez.peliculas.beans.Pelicula;
import gt.com.kinal.juanlopez.peliculas.beans.Titular;
import gt.com.kinal.juanlopez.peliculas.clsDetalleP;

public class PeliculaFragment extends Fragment {
    public ListView lista;
    public PeliculasAdapter adapter;

    ArrayList<Pelicula> listaPelicula = new ArrayList<Pelicula>();
    ArrayList<Pelicula> listBackupDatas = new ArrayList<Pelicula>();

    public SQLiteDatabase db;
    public BDD_sqlite sqlite;

    public PeliculaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pelicula, container, false);
        lista = (ListView)view.findViewById(R.id.listView);
        llenarLista();

        return view;
    }

    public void llenarLista() {
        listaPelicula.clear();
        listBackupDatas.clear();

        sqlite = new BDD_sqlite(getActivity().getBaseContext());
        db = sqlite.getReadableDatabase();

        String Sql = "SELECT * FROM PELICULA";

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
                        getActivity().getApplicationContext()).inflate(
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
                    String cat_ID = listaPelicula.get(pos).getTitulo();
                    Aler(cat_ID);
                }
            });

            holder.Descript.setTag(position);
            holder.Descript.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (Integer) v.getTag();
                    String cat_ID = listaPelicula.get(pos).getTitulo();
                    Aler(cat_ID);
                }
            });

            holder.thumb.setTag(position);
            holder.thumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (Integer) v.getTag();
                    String cat_ID = listaPelicula.get(pos).getTitulo();
                    Aler(cat_ID);
                }
            });

            return convertView;
        }
    }

    public void Aler(String titulo) {

        final String ti = titulo;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(titulo);

        builder.setMessage(titulo);

        builder.setPositiveButton("Agregar favoritos", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                sqlite = new BDD_sqlite(getActivity().getBaseContext());
                db = sqlite.getReadableDatabase();

                String sql1 = "UPDATE PELICULA SET ESTADO='0' WHERE TITULO='" + ti + "'";
                db.execSQL(sql1);
                db.close();
            }
        });

        builder.setNegativeButton("Detalles", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent iMod = new Intent(getActivity(), clsDetalleP.class);
                iMod.putExtra("titulo", ti);
                startActivity(iMod);
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

    private static class PlayerViewHolder {
        public ImageView thumb;
        public TextView Titulo;
        public TextView Descript;
    }


}
