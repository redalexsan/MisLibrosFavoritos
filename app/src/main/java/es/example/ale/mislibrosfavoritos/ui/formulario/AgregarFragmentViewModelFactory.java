package es.example.ale.mislibrosfavoritos.ui.formulario;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import es.example.ale.mislibrosfavoritos.data.Repository;

public class AgregarFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repository;

    public AgregarFragmentViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AgregarFragmentViewModel(repository);
    }
}
