package gt.com.kinal.juanlopez.peliculas.fragment;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import gt.com.kinal.juanlopez.peliculas.BaseDatos.BDD_sqlite;
import gt.com.kinal.juanlopez.peliculas.R;
import gt.com.kinal.juanlopez.peliculas.beans.Usuario;


public class PerfilFragment extends Fragment {
    private EditText editTextUsuario;
    private EditText editTextCorreo;
    private EditText editTextPassAnt;
    private EditText editTextPassNew;
    private EditText editTextPass;
    private Button buttonEdit;

    private String NAME = "Yo";
    private String EMAIL = "yo@kinal.org.gt";
    private String PASSWORD = "123";

    public SQLiteDatabase db;
    public BDD_sqlite sqlite;

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        // Inflate the layout for this fragment
        editTextUsuario = (EditText)view.findViewById(R.id.edtUsuarioPer);
        editTextCorreo = (EditText)view.findViewById(R.id.edtCorreoPer);
        editTextPassAnt = (EditText)view.findViewById(R.id.edtPasswordAntPer);
        editTextPassNew = (EditText)view.findViewById(R.id.edtPasswordNewPer);
        editTextPass = (EditText)view.findViewById(R.id.edtPasswordPer);

        buttonEdit = (Button)view.findViewById(R.id.btnModifPer);

        Login();

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editar();
            }
        });

        return view;
    }
    public void Login() {
        sqlite = new BDD_sqlite(getActivity().getBaseContext());
        db = sqlite.getReadableDatabase();

        String Sql = "SELECT NOMBRE,CORREO,PASSWORD FROM USUARIOS WHERE ESTADO='1'";

        Cursor cc = db.rawQuery(Sql, null);
        Usuario obj;
        int valida = 0;
        if (cc.moveToFirst()) {
            do {
                valida++;
                NAME = cc.getString(0);
                EMAIL = cc.getString(1);
                PASSWORD = cc.getString(2);
            } while (cc.moveToNext());
        }
        db.close();
        if (valida > 0 ){
            editTextUsuario.setText(NAME);
            editTextCorreo.setText(EMAIL);
            editTextPass.setText(PASSWORD);
        }
    }
    public void Editar(){

    }

}
