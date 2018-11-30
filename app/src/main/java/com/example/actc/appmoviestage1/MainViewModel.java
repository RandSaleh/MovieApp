package com.example.actc.appmoviestage1;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class MainViewModel extends AndroidViewModel {


    private LiveData<List<Movie>> All ;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDataBase database = AppDataBase.getInstance(this.getApplication());
        All = database.getMovieDao().loadAllMovies();

    }

    public LiveData<List<Movie>>  getAll(){
        return All;
    }



}
