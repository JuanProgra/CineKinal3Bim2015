package gt.com.kinal.juanlopez.peliculas;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import gt.com.kinal.juanlopez.peliculas.BaseDatos.BDD_sqlite;
import gt.com.kinal.juanlopez.peliculas.beans.Usuario;


public class clsRegistro extends ActionBarActivity {

    private Toolbar mToolbar;

    private EditText edtUsuario;
    private EditText edtPassword;
    private EditText edtPasswordC;
    private EditText edtNombre;
    private EditText edtEmail;

    private Button btnRegistro;

    public SQLiteDatabase db;
    public BDD_sqlite sqlite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cls_registro);


        edtUsuario = (EditText)findViewById(R.id.edtUser);
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        edtPasswordC = (EditText)findViewById(R.id.edtPasswordC);
        edtNombre = (EditText)findViewById(R.id.edtNombre);
        edtEmail = (EditText)findViewById(R.id.edtEmail);

        btnRegistro = (Button)findViewById(R.id.btnRegist);


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ing(getApplicationContext());
            }
        });
        setupDrawer();
    }

    private void setupDrawer() {
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cls_registro, menu);
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
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    public void Ing(Context context){
        if (edtUsuario.getText().length() > 0){
            if (edtPassword.getText().length() > 0){
                if (edtPasswordC.getText().length() > 0){

                    if (edtPassword.getText().toString().equals(edtPasswordC.getText().toString())){

                        sqlite = new BDD_sqlite(getBaseContext());
                        db = sqlite.getReadableDatabase();

                        String Sql = "SELECT * FROM USUARIOS WHERE USUARIO='"+edtUsuario.getText().toString()+"'";

                        Cursor cc = db.rawQuery(Sql, null);
                        Usuario obj;
                        int valida = 0;
                        if (cc.moveToFirst())
                        {
                            do {
                                valida++;
                            } while (cc.moveToNext());
                        }
                        db.close();
                        if (valida <= 0 ){
                            ContentValues usuarioConte = new ContentValues();

                            try{

                                sqlite = new BDD_sqlite(getBaseContext());
                                db = sqlite.getReadableDatabase();

                                usuarioConte.put("USUARIO", edtUsuario.getText().toString());
                                usuarioConte.put("PASSWORD", edtPassword.getText().toString());
                                usuarioConte.put("NOMBRE", edtNombre.getText().toString());
                                usuarioConte.put("CORREO", edtEmail.getText().toString());
                                usuarioConte.put("ESTADO", "1");

                                db.insert("USUARIOS", null, usuarioConte);
                                db.close();

                                Intent intent =  new Intent(clsRegistro.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } catch (Exception e) {
                                db.close();
                                Toast toast = Toast.makeText(getApplicationContext(),"3)" + e.toString(), Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }else
                        {
                            Toast toast = Toast.makeText(getApplicationContext(),"El nombre de usuario ya existe", Toast.LENGTH_SHORT);
                            toast.show();
                        }

                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(),"Las contraseñas no son inguales", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),"Confirme su contraseña", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }else{
                Toast toast = Toast.makeText(getApplicationContext(),"Ingrese una contraseña", Toast.LENGTH_SHORT);
                toast.show();
            }
        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"Ingrese un nombre de usuario", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
