package com.example.uasprakmobile_fathanandhikadaffaputraadhiwibowo_2410501056_b;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Favorit.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract FavoritDao favoritDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "endemik_db")
                    .allowMainThreadQueries() // For simplicity in this example
                    .build();
        }
        return instance;
    }
}
