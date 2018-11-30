package com.example.actc.appmoviestage1;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class MoviewDetaisViewModelFactory extends ViewModelProvider.NewInstanceFactory {



        private final AppDataBase mDb;
        private final String fMovieId;

        public MoviewDetaisViewModelFactory(AppDataBase database,String fMovieIdId) {
            mDb = database;
           this.fMovieId = fMovieIdId;
        }



    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new MovieDetailsViewModel(mDb, fMovieId);
    }



    }

