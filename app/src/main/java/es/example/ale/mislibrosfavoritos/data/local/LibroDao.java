package es.example.ale.mislibrosfavoritos.data.local;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import es.example.ale.mislibrosfavoritos.data.model.Libro;

@Dao
public interface LibroDao {

    @Query("SELECT * FROM libros")
    LiveData<List<Libro>> queryAllLibros();

    @Query("SELECT * FROM libros WHERE id = :libroID")
    LiveData<Libro> queryLibro(long libroID);

    @Insert
    void insertLibro(Libro libro);

    @Delete
    void deletenLibro(Libro libro);

    @Update
    void updateLibro(Libro libro);
}
