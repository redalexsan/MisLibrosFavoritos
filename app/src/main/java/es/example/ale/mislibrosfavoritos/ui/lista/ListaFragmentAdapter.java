package es.example.ale.mislibrosfavoritos.ui.lista;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import es.example.ale.mislibrosfavoritos.DataBottomSheetDialogFragment;
import es.example.ale.mislibrosfavoritos.R;
import es.example.ale.mislibrosfavoritos.data.model.Libro;

public class ListaFragmentAdapter extends ListAdapter<Libro, ListaFragmentAdapter.ViewHolder> {

    private NavController navController;

    protected ListaFragmentAdapter(NavController navController) {
        super(new DiffUtil.ItemCallback<Libro>() {
            @Override
            public boolean areItemsTheSame(@NonNull Libro oldItem, @NonNull Libro newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Libro oldItem, @NonNull Libro newItem) {
                return TextUtils.equals(oldItem.getFecha(),newItem.getFecha()) && TextUtils.equals(oldItem.getAutor(),newItem.getAutor()) && TextUtils.equals(oldItem.getImagen(),newItem.getImagen()) && TextUtils.equals(oldItem.getTitulo(),newItem.getTitulo());
            }
        });
        this.navController = navController;
    }


    @NonNull
    @Override
    public ListaFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_lista_item,parent,false), navController);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaFragmentAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    protected Libro getItem(int position) {
        return super.getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView titulo, autor, fecha;
        private final ImageView imagen;

        public ViewHolder(@NonNull View itemView, NavController navController) {
            super(itemView);
            titulo = ViewCompat.requireViewById(itemView,R.id.lblTitulo);
            autor = ViewCompat.requireViewById(itemView,R.id.lblAutor);
            fecha = ViewCompat.requireViewById(itemView,R.id.lblFecha);
            imagen = ViewCompat.requireViewById(itemView,R.id.imgLibro);
            itemView.setOnClickListener(v -> showBottomSheetDialogFragment(getItem(getAdapterPosition())));
        }

        private void showBottomSheetDialogFragment(Libro item) {
            DataBottomSheetDialogFragment.newInstance(item.getId()).show(
                    ((AppCompatActivity) itemView.getContext()).getSupportFragmentManager(),DataBottomSheetDialogFragment.class.getSimpleName()
            );
        }

        public void bind(Libro item) {
            titulo.setText(item.getTitulo());
            autor.setText(item.getAutor());
            fecha.setText(item.getFecha());
            Picasso.with(titulo.getContext()).load(item.getImagen()).into(imagen);
        }
    }
}
