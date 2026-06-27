package com.example.uasprakmobile_fathanandhikadaffaputraadhiwibowo_2410501056_b;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface FavoritDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Favorit favorit);

    @Delete
    void delete(Favorit favorit);

    @Query("SELECT * FROM favorit")
    List<Favorit> getAllFavorit();

    @Query("SELECT EXISTS(SELECT * FROM favorit WHERE id = :id)")
    boolean isFavorit(String id);
}
