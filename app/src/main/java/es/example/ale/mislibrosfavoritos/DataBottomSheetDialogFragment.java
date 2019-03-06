package es.example.ale.mislibrosfavoritos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import es.example.ale.mislibrosfavoritos.data.RepositoryImpl;
import es.example.ale.mislibrosfavoritos.data.local.AppDatabase;
import es.example.ale.mislibrosfavoritos.data.local.LibroDao;
import es.example.ale.mislibrosfavoritos.data.model.Libro;
import es.example.ale.mislibrosfavoritos.databinding.FragmentBottomsheetBinding;
import es.example.ale.mislibrosfavoritos.ui.lista.ListaFragmentViewModel;
import es.example.ale.mislibrosfavoritos.ui.lista.ListaFragmentViewModelFactory;

public class DataBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private static final String ARG_LIBRO = "ARGL_LIBRO";

    private long idLibro;
    private ListaFragmentViewModel listaFragmentViewModel;
    private FragmentBottomsheetBinding binding;

    public static DataBottomSheetDialogFragment newInstance(long idLibro){
        DataBottomSheetDialogFragment dfrg = new DataBottomSheetDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putLong(ARG_LIBRO,idLibro);
        dfrg.setArguments(arguments);
        return  dfrg;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBottomsheetBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Objects.requireNonNull(getArguments());
        LibroDao libroDao = AppDatabase.getInstance(requireContext()).libroDao();
        RepositoryImpl repository = new RepositoryImpl(libroDao);
        listaFragmentViewModel = ViewModelProviders.of(this,new ListaFragmentViewModelFactory(repository)).get(ListaFragmentViewModel.class);
        obationArguments();
        initViews();
    }

    private void initViews() {
        listaFragmentViewModel.queryLibro(idLibro).observe(this, libro -> {
            binding.txtSinopsis.setText(libro.getSinopsis());
            binding.txtTitulo.setText(libro.getTitulo());
        });

        binding.imageClose.setOnClickListener(v -> closeBottomSheet());
    }

    private void closeBottomSheet() {
        dismiss();
    }

    private void obationArguments() {
        if(getArguments() != null){
            idLibro = getArguments().getLong(ARG_LIBRO);
        }
    }
}

