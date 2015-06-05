package gt.com.kinal.juanlopez.peliculas.beans;

/**
 * Created by Godinez Miranda on 05/06/2015.
 */
public class Titular {
    private String titulo;
    private String subtitulo;
    private String descripcion;

    public String getTitulo() {
        return titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Titular(String titulo, String subtitulo, String descripcion) {
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.descripcion = descripcion;
    }
}
