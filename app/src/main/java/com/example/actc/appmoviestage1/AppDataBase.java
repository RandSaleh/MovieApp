package com.example.actc.appmoviestage1;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Movie.class}, version = 2, exportSchema = false)

public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase dataBase;
    public static String tableName = "MovieDataBase ";

    public abstract movieDao getMovieDao();

    public static AppDataBase getInstance(Context context) {

        if (dataBase == null) {
            synchronized (new Object()) {
                dataBase = Room.databaseBuilder(context.getApplicationContext()
                        , AppDataBase.class,
                        AppDataBase.tableName)
                        .fallbackToDestructiveMigration()
                        .build();

            }
        }
        return dataBase;
    }


}
