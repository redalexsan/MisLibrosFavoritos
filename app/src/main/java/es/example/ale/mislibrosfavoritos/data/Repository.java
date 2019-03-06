package es.example.ale.mislibrosfavoritos.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import es.example.ale.mislibrosfavoritos.data.model.Libro;

public interface Repository {

    LiveData<List<Libro>> queryAllLibros();
    LiveData<Libro> queryLibro(long libroID);
    void insertLibro(Libro libro);
    void deleteLibro(Libro libro);
    void updateLibro(Libro libro);
}
