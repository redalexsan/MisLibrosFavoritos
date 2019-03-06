package es.example.ale.mislibrosfavoritos.data;

import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import es.example.ale.mislibrosfavoritos.data.local.LibroDao;
import es.example.ale.mislibrosfavoritos.data.model.Libro;

public class RepositoryImpl implements Repository {

    private LibroDao libroDao;

    public RepositoryImpl(LibroDao libroDao) {
        this.libroDao = libroDao;
    }

    @Override
    public LiveData<List<Libro>> queryAllLibros() {
        return libroDao.queryAllLibros();
    }

    @Override
    public LiveData<Libro> queryLibro(long libroID) {
        return libroDao.queryLibro(libroID);
    }

    @Override
    public void insertLibro(final Libro libro) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> libroDao.insertLibro(libro));
    }

    @Override
    public void deleteLibro(Libro libro) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> libroDao.deletenLibro(libro));
    }

    @Override
    public void updateLibro(Libro libro) {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> libroDao.updateLibro(libro));
    }
}
