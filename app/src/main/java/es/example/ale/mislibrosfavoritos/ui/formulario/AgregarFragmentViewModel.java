package es.example.ale.mislibrosfavoritos.ui.formulario;

import androidx.lifecycle.ViewModel;
import es.example.ale.mislibrosfavoritos.data.Repository;
import es.example.ale.mislibrosfavoritos.data.model.Libro;

public class AgregarFragmentViewModel extends ViewModel {
    private final Repository repository;

    public AgregarFragmentViewModel(Repository repository) {
        this.repository = repository;
    }

    public void agregarLibro(Libro libro){
        repository.insertLibro(libro);
    }
}
