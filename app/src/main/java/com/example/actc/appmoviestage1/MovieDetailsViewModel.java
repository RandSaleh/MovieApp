package com.example.actc.appmoviestage1;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

public class MovieDetailsViewModel extends ViewModel {

    private LiveData<Movie> movie;

    public MovieDetailsViewModel(AppDataBase database, String movieId) {

        movie = database.getMovieDao().findMovieById(movieId);

    }


    public LiveData<Movie> getCourse() {
        return movie;
    }

}

