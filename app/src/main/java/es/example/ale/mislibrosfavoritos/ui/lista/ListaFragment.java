package es.example.ale.mislibrosfavoritos.ui.lista;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import es.example.ale.mislibrosfavoritos.MainActivityViewModel;
import es.example.ale.mislibrosfavoritos.R;
import es.example.ale.mislibrosfavoritos.data.RepositoryImpl;
import es.example.ale.mislibrosfavoritos.data.local.AppDatabase;
import es.example.ale.mislibrosfavoritos.data.local.LibroDao;
import es.example.ale.mislibrosfavoritos.data.model.Libro;
import es.example.ale.mislibrosfavoritos.databinding.FragmentListContentBinding;


public class ListaFragment extends Fragment {

    private FragmentListContentBinding binding;
    private ListaFragmentViewModel viewModel;
    private NavController navController;
    private ListaFragmentAdapter listAdapter;
    private Boolean deshacerHabilitado = true;
    private MainActivityViewModel activityViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListContentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activityViewModel = ViewModelProviders.of(requireActivity()).get(MainActivityViewModel.class);
        navController = NavHostFragment.findNavController(this);
        LibroDao libroDao = AppDatabase.getInstance(getContext()).libroDao();
        RepositoryImpl repository = new RepositoryImpl(libroDao);
        viewModel = ViewModelProviders.of(this,new ListaFragmentViewModelFactory(repository)).get(ListaFragmentViewModel.class);

        viewModel.getLibros().observe(this,libros -> {
            listAdapter.submitList(libros);
            binding.lblEmptyView.setVisibility(libros.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        });
        initViews();

        activityViewModel.getDeshacer().observe(this, deshacer -> deshacerHabilitado = deshacer);
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
                Libro libroBorrado = listAdapter.getItem(viewHolder.getAdapterPosition());
                viewModel.deleteLibro(libroBorrado);
                if(deshacerHabilitado)
                    Snackbar.make(binding.lblEmptyView,getString(R.string.libroBorrado),Snackbar.LENGTH_SHORT)
                            .setAction(getString(R.string.deshacer), v -> viewModel.insertLibro(libroBorrado)).show();
                else
                    Snackbar.make(binding.lblEmptyView,getString(R.string.libroBorrado),Snackbar.LENGTH_SHORT).show();
            }
        });
        itemTouchHelper.attachToRecyclerView(lstLibros);

        binding.btnFloat.setOnClickListener(v -> navController.navigate(R.id.action_listaFragment_to_agregarFragment));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_settings,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        navController.navigate(R.id.action_listaFragment_to_settingsFragment);
        return true;
    }
}
