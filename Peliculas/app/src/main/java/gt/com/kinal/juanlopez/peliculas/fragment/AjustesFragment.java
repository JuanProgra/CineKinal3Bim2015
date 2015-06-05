package gt.com.kinal.juanlopez.peliculas.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import gt.com.kinal.juanlopez.peliculas.BaseDatos.BDD_sqlite;
import gt.com.kinal.juanlopez.peliculas.R;
import gt.com.kinal.juanlopez.peliculas.beans.Pelicula;
import gt.com.kinal.juanlopez.peliculas.clsDetalleP;


public class AjustesFragment extends Fragment {

    public AjustesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ajustes, container, false);
        // Inflate the layout for this fragment


        return view;
    }

}
