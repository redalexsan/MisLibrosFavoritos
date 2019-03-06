package es.example.ale.mislibrosfavoritos.ui.lista;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import es.example.ale.mislibrosfavoritos.data.Repository;

public class ListaFragmentViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repository;

    public ListaFragmentViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ListaFragmentViewModel(repository);
    }
}
