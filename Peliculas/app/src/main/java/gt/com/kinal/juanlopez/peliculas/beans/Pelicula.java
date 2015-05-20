package gt.com.kinal.juanlopez.peliculas.beans;

/**
 * Created by Godinez Miranda on 20/05/2015.
 */
public class Pelicula {
    public String Titulo;
    public String Descripcion;
    public int Img;

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public int getImg() {
        return Img;
    }

    public void setImg(int img) {
        Img = img;
    }
}
