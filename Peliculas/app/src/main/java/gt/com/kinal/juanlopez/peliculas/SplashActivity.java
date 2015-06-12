package gt.com.kinal.juanlopez.peliculas;

import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import gt.com.kinal.juanlopez.peliculas.BaseDatos.BDD_sqlite;


public class SplashActivity extends ActionBarActivity {
    private static final long SPLASH_SCREEN_DELAY = 3000;
    public SQLiteDatabase db;
    public BDD_sqlite sqlite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        llenarPeliculas();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent().setClass(
                        SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
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
            peliculaConte.put("DESCRIPCION", "Cuando Tony Stark intenta reactivar un programa sin uso que tiene como objetivo de mantener la paz, las cosas comienzan a torcerse y los heroes mas poderosos de la Tierra, incluyendo a Iron Man, Capitan America, Thor, El Increible Hulk, Viuda Negra y Ojo de Halcon, se veran ante su prueba definitiva cuando el destino del planeta se ponga en juego. Cuando el villano Ultron emerge, le correspondera a Los Vengadores detener sus terribles planes, que junto a incomodas alianzas llevaran a una inesperada accion que allanara el camino para una epica y unica aventura.");
            peliculaConte.put("ESTADO", "1");

            db.insert("PELICULA", null, peliculaConte);
            db.close();

            db = sqlite.getReadableDatabase();

            peliculaConte.put("TITULO", "Clown: El Payaso del mal");
            peliculaConte.put("DESCRIPCION", "Un padre decide compra un traje de payaso para animar a su hijo en su sexto cumpleanos. Tras la fiesta se da cuenta de que es incapaz de quitarselo y su personalidad comienza a sufrir terrorificos cambios. El y su familia deberan intentar quitarselo en una carrera contra el tiempo para terminar con la maldicion, antes de que se complete la transformacion y se convierta en un homicida con zapatos muy grandes.");
            peliculaConte.put("ESTADO", "1");

            db.insert("PELICULA", null, peliculaConte);
            db.close();

            db = sqlite.getReadableDatabase();

            peliculaConte.put("TITULO", "Tomorrowland: El Mundo del manana");
            peliculaConte.put("DESCRIPCION", "Unidos por el mismo destino, un adolescente inteligente y optimista lleno de curiosidad cientifica y un antiguo nino prodigio inventor hastiado por las desilusiones se embarcan en una peligrosa mision para desenterrar los secretos de un enigmatico lugar localizado en algun lugar del tiempo y el espacio conocido en la memoria colectiva como Tomorrowland");
            peliculaConte.put("ESTADO", "1");

            db.insert("PELICULA", null, peliculaConte);
            db.close();

            db = sqlite.getReadableDatabase();

            peliculaConte.put("TITULO", "Heroe de Centro Comercial 2");
            peliculaConte.put("DESCRIPCION", "Kevin James vuelve a interpretar a Paul Blart, el guardia de seguridad que en esta ocasion se dirige a Las Vegas para atender una Exposicion sobre su ramo de trabajo y aprovechara para llevarse a su hija Maya (Raini Rodriguez) para pasar tiempo juntos antes de que ella se vaya a estudiar fuera. Mientras esta en la convencion, Paul sin darse cuenta descubre que se lleva a cabo un atraco, asa que como buen heroe, debera detener a los criminales.");
            peliculaConte.put("ESTADO", "1");

            db.insert("PELICULA", null, peliculaConte);
            db.close();
        } catch (SQLException e) {
            db.close();
            String message = e.toString();
            Toast.makeText(this, "0)" + message, Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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
}
