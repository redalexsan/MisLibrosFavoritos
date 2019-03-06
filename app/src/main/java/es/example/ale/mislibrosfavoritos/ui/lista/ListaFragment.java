package es.example.ale.mislibrosfavoritos.ui.lista;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.example.ale.mislibrosfavoritos.R;
import es.example.ale.mislibrosfavoritos.data.Repository;
import es.example.ale.mislibrosfavoritos.data.RepositoryImpl;
import es.example.ale.mislibrosfavoritos.data.local.AppDatabase;
import es.example.ale.mislibrosfavoritos.data.local.LibroDao;
import es.example.ale.mislibrosfavoritos.data.model.Libro;
import es.example.ale.mislibrosfavoritos.databinding.FragmentListaBinding;


public class ListaFragment extends Fragment {

    private FragmentListaBinding binding;
    private ListaFragmentViewModel viewModel;
    private NavController navController;
    private ListaFragmentAdapter listAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListaBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        LibroDao libroDao = AppDatabase.getInstance(getContext()).libroDao();
        RepositoryImpl repository = new RepositoryImpl(libroDao);
        viewModel = ViewModelProviders.of(this,new ListaFragmentViewModelFactory(repository)).get(ListaFragmentViewModel.class);

        viewModel.getLibros().observe(this,libros -> listAdapter.submitList(libros));
        initViews();
    }

    private void initViews() {
        RecyclerView lstLibros = binding.lstBooks;
        listAdapter = new ListaFragmentAdapter(navController);
        lstLibros.setHasFixedSize(true);
        lstLibros.setLayoutManager(new LinearLayoutManager(requireContext()));
        lstLibros.setItemAnimator(new DefaultItemAnimator());
        lstLibros.setAdapter(listAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewModel.deleteLibro(listAdapter.getItem(viewHolder.getAdapterPosition()));
            }
        });
        itemTouchHelper.attachToRecyclerView(lstLibros);

        Libro libro = new Libro("Titulo Prueba","Autor Prueba","2019","https://imagessl4.casadellibro.com/a/l/t0/84/9788490193884.jpg");
        binding.btnFloat.setOnClickListener(v -> viewModel.insertLibro(libro));
    }
}
