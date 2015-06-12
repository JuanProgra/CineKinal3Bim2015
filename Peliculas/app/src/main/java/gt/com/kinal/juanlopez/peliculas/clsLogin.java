package gt.com.kinal.juanlopez.peliculas;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import gt.com.kinal.juanlopez.peliculas.BaseDatos.BDD_sqlite;
import gt.com.kinal.juanlopez.peliculas.beans.Usuario;


public class clsLogin extends ActionBarActivity {

    private Toolbar mToolbar;

    private EditText edtUser;
    private EditText edtPassword;

    private Button btnLogin;
    private Button btnRegi;

    private String estado;

    public SQLiteDatabase db;
    public BDD_sqlite sqlite;

    private CheckBox checkBox;

    ArrayList<Usuario> listaUsu = new ArrayList<Usuario>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cls_login);

        estado = "0";

        edtUser = (EditText) findViewById(R.id.edtUserL);
        edtPassword = (EditText) findViewById(R.id.edtPasswordL);

        checkBox = (CheckBox) findViewById(R.id.Recuerdame);

        checkBox.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    estado = "1";
                    mensaje();

                } else {
                    estado = "0";
                    mensajeNo();

                }

            }
        });

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegi = (Button) findViewById(R.id.btnRegisL);

        btnRegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(clsLogin.this, clsRegistro.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

        setupDrawer();
    }

    public void mensaje() {
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }

    public void mensajeNo() {
        Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
    }

    private void setupDrawer() {
        mToolbar = (Toolbar) findViewById(R.id.app_bar);

        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cls_login, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void Login() {
        String usuario = "";
        String password = "";

        sqlite = new BDD_sqlite(getBaseContext());
        db = sqlite.getReadableDatabase();

        String Sql = "SELECT PASSWORD FROM USUARIOS WHERE USUARIO='" + edtUser.getText().toString() + "'";

        Cursor cc = db.rawQuery(Sql, null);
        int valida = 0;
        if (cc.moveToFirst()) {
            do {
                password = cc.getString(0);
                valida++;
            } while (cc.moveToNext());
        }
        db.close();

        if (edtUser.getText().length() > 0) {
            if (edtPassword.getText().length() > 0) {
                if (valida > 0) {
                    if (password.equals(edtPassword.getText().toString())) {

                        sqlite = new BDD_sqlite(getBaseContext());
                        db = sqlite.getReadableDatabase();
                        try {
                            String sql1 = "UPDATE USUARIOS SET ESTADO='1',ESTADOLOG='"+estado+"' WHERE USUARIO='" + edtUser.getText().toString() + "'";
                            Toast.makeText(this, sql1, Toast.LENGTH_SHORT).show();
                            db.execSQL(sql1);
                            db.close();

                            Intent intent = new Intent(clsLogin.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (SQLException e) {
                            db.close();
                            String message = e.toString();
                            Toast.makeText(this, "0)" + message, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "La contraseña no se valida", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }else{
                    Toast.makeText(this, "Usuario no registrado verificar credenciales", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Ingrese su contraseña", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Ingrese su nombre de usuario", Toast.LENGTH_SHORT).show();
        }
    }
}
