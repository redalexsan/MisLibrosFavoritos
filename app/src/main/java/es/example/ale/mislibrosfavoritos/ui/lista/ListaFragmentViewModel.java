package es.example.ale.mislibrosfavoritos.ui.lista;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import es.example.ale.mislibrosfavoritos.data.Repository;
import es.example.ale.mislibrosfavoritos.data.model.Libro;

public class ListaFragmentViewModel extends ViewModel {

    private final Repository repository;
    private LiveData<List<Libro>> libros;

    public ListaFragmentViewModel(Repository repository) {
        this.repository = repository;
        libros = repository.queryAllLibros();
    }

    public LiveData<List<Libro>> getLibros(){
        return libros;
    }

    public void deleteLibro(Libro libro){
        repository.deleteLibro(libro);
    }

    public void insertLibro(Libro libro){
        repository.insertLibro(libro);
    }

    public LiveData<Libro> queryLibro(long id){ return repository.queryLibro(id);}
}
