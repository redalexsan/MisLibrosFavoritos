package es.example.ale.mislibrosfavoritos.ui.formulario;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import es.example.ale.mislibrosfavoritos.R;
import es.example.ale.mislibrosfavoritos.data.RepositoryImpl;
import es.example.ale.mislibrosfavoritos.data.local.AppDatabase;
import es.example.ale.mislibrosfavoritos.data.local.LibroDao;
import es.example.ale.mislibrosfavoritos.data.model.Libro;
import es.example.ale.mislibrosfavoritos.databinding.FragmentAgregarBinding;
import es.example.ale.mislibrosfavoritos.utils.KeyboardUtils;
import es.example.ale.mislibrosfavoritos.utils.ValidationUtils;


public class AgregarFragment extends Fragment {

    private NavController navController;
    private FragmentAgregarBinding binding;
    private AgregarFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAgregarBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        LibroDao libroDao = AppDatabase.getInstance(getContext()).libroDao();
        RepositoryImpl repository = new RepositoryImpl(libroDao);
        viewModel = ViewModelProviders.of(this,new AgregarFragmentViewModelFactory(repository)).get(AgregarFragmentViewModel.class);

        initViews();
    }

    private void initViews() {
        binding.txtURL.setOnFocusChangeListener((v, hasFocus) -> {
            if(!hasFocus)
                if(!TextUtils.isEmpty(binding.txtURL.getText().toString()))
                    Picasso.with(requireContext()).load(binding.txtURL.getText().toString()).into(binding.imageView);
        });
        
        binding.floatingActionButton.setOnClickListener(v -> {
            KeyboardUtils.hideSoftKeyboard(requireActivity());
            validar();
        });
    }

    private void validar() {
        boolean validarTitulo = !TextUtils.isEmpty(binding.txtTitlulo.getText().toString());
        boolean validarAutor = !TextUtils.isEmpty(binding.txtAutor.getText().toString());
        boolean validarAnio = !TextUtils.isEmpty(binding.txtAnio.getText().toString());
        boolean validarURL = ValidationUtils.isValidUrl(binding.txtURL.getText().toString());

        if(validarTitulo && validarAutor && validarAnio && validarURL){
            showDialog();
        }
        else{
            setError(binding.txtTitlulo,validarTitulo);
            setError(binding.txtAutor,validarAutor);
            setError(binding.txtAnio,validarAnio);
            setError(binding.txtURL,validarURL);
        }
    }

    private void showDialog() {
        String yesText="Guardar", noText="Cancelar";
        AlertDialog.Builder ad = new AlertDialog.Builder(requireContext(),R.style.Theme_AppCompat_Light_Dialog_Alert);


        ad.setTitle(R.string.titleDialog);
        ad.setMessage(getString(R.string.messageDialog));
        ad.setPositiveButton(yesText,((dialog, which) -> insertLibro()));
        ad.setNegativeButton(noText, (dialog, which) -> dialog.dismiss());
        Dialog dialog = ad.create();

        dialog.show();
    }

    private void insertLibro() {
        String titulo = binding.txtTitlulo.getText().toString(), autor = binding.txtAutor.getText().toString(), anio = binding.txtAnio.getText().toString(), url = binding.txtURL.getText().toString();
        Libro libro = new Libro(titulo,autor,anio,url);

        viewModel.agregarLibro(libro);
        navController.popBackStack();
    }

    private void setError(TextInputEditText editText, boolean validar) {
        if(validar)
            editText.setError(null);
        else
            editText.setError(getString(R.string.campoVacio));
    }

}
