package es.example.ale.mislibrosfavoritos.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "libros" )
public class Libro {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "titulo")
    private String titulo;
    @ColumnInfo(name = "autor")
    private String autor;
    @ColumnInfo(name = "fecha")
    private String fecha;
    @ColumnInfo(name = "imagen")
    private String imagen;

    public Libro(String titulo, String autor, String fecha, String imagen) {
        this.titulo = titulo;
        this.autor = autor;
        this.fecha = fecha;
        this.imagen = imagen;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
