package com.example.actc.appmoviestage1;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.PushbackInputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.BufferUnderflowException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements movieAdapter.ListMovieClickListener, Serializable {
    connect con_all;
    static int currentVisiblePosition = 0;
    public static String savedSort;
    private movieAdapter mAdapter;
    public static String MOST_POP = "most_popular";
    public static String MOST_RATE = "most_rate";
    public static String FAVORITE = "favorite";
    public static String SAVE_SORT = "save_sort";
    final static String Base_url = "https://api.themoviedb.org";
    final static String SORT_BY_PARAM = "sort_by";
    final static String Api = "f96a881187713e61d7b05dd581ac8c4f";
    final static String API_KEY_PARAM = "api_key";
    public boolean processed = false;
    private static final int NUM_LIST_ITEMS = 16;
    public static RecyclerView mNumbersList;
    public static String SAVED_LAYOUT_MANAGER = "saved_layout_manager";
    public static String LAST_SCROLL = "last_scroll";
    Toast mToast;
    public static GridLayoutManager grid;
    public String link = null;

    NetworkInfo activeNetwork;
    boolean isConnected;

    public MainActivity() throws MalformedURLException {
        con_all = new connect();

    }

//---


    //-----
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putString(SAVE_SORT, savedSort);

        currentVisiblePosition = ((GridLayoutManager) mNumbersList.getLayoutManager()).findFirstVisibleItemPosition();

        outState.putInt(LAST_SCROLL, currentVisiblePosition);




    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.getInt(LAST_SCROLL) != 0) {
                currentVisiblePosition = savedInstanceState.getInt(LAST_SCROLL);

            }

            if (savedInstanceState.getString(SAVE_SORT).equalsIgnoreCase(MOST_POP)) {
                displayMostPop();
            } else if (savedInstanceState.getString(SAVE_SORT).equalsIgnoreCase(MOST_RATE)) {
                displayTopRated();
            } else if (savedInstanceState.getString(SAVE_SORT).equalsIgnoreCase(FAVORITE)) {

                displayFavorite();

            }

        }


//------


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //    settings = getApplicationContext().getSharedPreferences(LAST_SCROLL, 0);


        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null)
            activeNetwork = cm.getActiveNetworkInfo();


        if (activeNetwork != null) {
            isConnected = activeNetwork.isConnected();
        } else {
            isConnected = false;
        }


        if (isConnected) {
            try {


                if (savedInstanceState == null) {

                    displayTopRated();


                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if (savedInstanceState != null) {


                if (savedInstanceState.getString(SAVE_SORT).equalsIgnoreCase(FAVORITE)) {

                    displayFavorite();
                }


            }

            FrameLayout fr = (FrameLayout) findViewById(R.id.background);
            fr.setBackgroundResource(R.drawable.oops);
            Toast.makeText(this, "You must be connected to the internet", Toast.LENGTH_LONG).show();

        }


    }

    @Override
    public void onclickListener(int pos) {

        System.out.println("public" + con_all.getDataSource().get(0).getOriginal_language());

        if (mToast != null) {
            mToast.cancel();
        }


        Movie send = con_all.getDataSource().get(pos);
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("sendObject",send);


        startActivity(intent);


    }


    public class connect extends AsyncTask<URL, Void, String> implements Serializable {
        ArrayList<Movie> DataSource = new ArrayList<Movie>();

        URL result_url = null;


        public connect() throws MalformedURLException {
            DataSource = new ArrayList<Movie>();
            System.out.println("here" + DataSource.isEmpty());


        }

        @Override
        protected String doInBackground(URL... params) {
            System.out.println("tp" + params[0]);
            result_url = params[0];
            String Results = null;
            try {
                Results = NetworkUtils.getResponseFromHttpUrl(result_url);

                System.out.println("line1" + Results);

            } catch (IOException e) {
                e.printStackTrace();
                processed = false;
                Toast.makeText(MainActivity.this, "Error connection ", Toast.LENGTH_LONG).show();
            }
            return Results;
        } //-- end of background


        @Override
        protected void onPostExecute(String Results) {

            if (Results != null && !Results.equals("")) {
                try {
                    ArrayList<Movie> temp = new ArrayList<Movie>();

                    temp = JsonParsing.parse(Results); /// member class
                    setDataSource(temp);


                    nowDisplay(getDataSource(), NUM_LIST_ITEMS);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error connection ", Toast.LENGTH_LONG).show();
                }
            }
        } //-- end of post excecute


        public ArrayList<Movie> getDataSource() {
            return DataSource;
        }


        public void setDataSource(ArrayList<Movie> m) {
            if (!(DataSource.isEmpty()))
                DataSource.removeAll(DataSource);


            DataSource.addAll(m);


        }


    }


    public void nowDisplay(ArrayList<Movie> DS, int num) {
        con_all.setDataSource(DS);
        mNumbersList = (RecyclerView) findViewById(R.id.rv_numbers);
        grid = new GridLayoutManager(this, 2);

        mAdapter = new movieAdapter(num, con_all.getDataSource(), this);


        ///////=============
        mNumbersList.setLayoutManager(grid);
        mNumbersList.setHasFixedSize(true);
        mNumbersList.setAdapter(mAdapter);


        ((GridLayoutManager) (mNumbersList.getLayoutManager())).scrollToPositionWithOffset(currentVisiblePosition, 0);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id_clicked = item.getItemId();
        if (id_clicked == R.id.most_pop) {
            currentVisiblePosition = 0;
            displayMostPop();
        } else if (id_clicked == R.id.most_rate) {
            currentVisiblePosition = 0;
            displayTopRated();
        } else if (id_clicked == R.id.favorite) {
            currentVisiblePosition = 0;
            displayFavorite();
        }

        return super.onOptionsItemSelected(item);
    }

    public String AssignAPIandQuery(String Query) {
        Uri buildUri = Uri.parse(Base_url).buildUpon()
                .path(Query)
                .appendQueryParameter(API_KEY_PARAM, Api)
                .build();
        String ready = buildUri.toString();
        return ready;

    }


    public void displayFavorite() {
        savedSort = FAVORITE;
        final ArrayList<Movie> favoriteMovie = new ArrayList<Movie>();

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getAll().observe(this, new android.arch.lifecycle.Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if (!(favoriteMovie.isEmpty())) {
                    favoriteMovie.clear();
                }
                for (int i = 0; i <= movies.size() - 1; i++) {
                    System.out.println("f " + i + "  " + movies.get(i).getOriginal_title());
                    favoriteMovie.add(movies.get(i));
                }
                for (int i = 0; i <= favoriteMovie.size() - 1; i++) {
                    System.out.println("f " + i + "  " + movies.get(i).getOriginal_title());
                }

                nowDisplay(favoriteMovie, favoriteMovie.size());
            }
        });
    }

    public void displayTopRated() {
        savedSort = MOST_RATE;
        try {
            connect con_most_rate = new connect();
            con_most_rate.execute(new URL(AssignAPIandQuery("3/movie/top_rated"))).get();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    public void displayMostPop() {

        try {
            savedSort = MOST_POP;
            connect con = new connect();
            con.execute(new URL(AssignAPIandQuery("3/movie/popular"))).get();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }


}