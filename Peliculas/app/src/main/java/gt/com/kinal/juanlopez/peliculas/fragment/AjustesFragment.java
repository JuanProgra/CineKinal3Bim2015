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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import gt.com.kinal.juanlopez.peliculas.BaseDatos.BDD_sqlite;
import gt.com.kinal.juanlopez.peliculas.R;
import gt.com.kinal.juanlopez.peliculas.beans.Pelicula;
import gt.com.kinal.juanlopez.peliculas.clsDetalleP;


public class AjustesFragment extends Fragment {
    final String[] datos = new String[] {"Argentina", "Guatemala", "Panama", "Mexico", "Colombia"};
    private Spinner paisOpciones;
    
    public AjustesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ajustes, container, false);
        // Inflate the layout for this fragment

        //Spinner Implementation
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, datos);
        paisOpciones = (Spinner)view.findViewById(R.id.SpinnerDatos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paisOpciones.setAdapter(adapter);
        paisOpciones.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(),"Seleccionado: " + parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        return view;
    }

}
