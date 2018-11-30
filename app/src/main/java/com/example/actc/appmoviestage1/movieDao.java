package com.example.actc.appmoviestage1;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface movieDao {



    @Query("SELECT * FROM  MovieModel")
    LiveData<List <Movie>> loadAllMovies();


    @Delete
    public  void Delete(Movie movie) ;


    @Insert
    public void Insert (Movie  movie);


    @Query("SELECT * FROM MovieModel WHERE id =:id ")
    public LiveData<Movie > findMovieById(String id);






}
