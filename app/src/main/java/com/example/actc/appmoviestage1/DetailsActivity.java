package com.example.actc.appmoviestage1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements Serializable {
    LinearLayout parentOfTrailer;
    Button dynamicButton;
    ImageView addFav;
    ArrayList<Uri> ReadyBuiltLinkYouTube;
    TextView txtOriginalTitle;
    TextView txtOriginalLanguage;
    TextView txtVoteAvarage;
    TextView txtDescription;
    TextView txtReleaseDate;
    ImageView posterImg;
    ImageView imgReview;
    ImageView backdropImg;
    private boolean ifExists = false;
    Movie readyToDisplay = null;

    public ArrayList<String> KEY;
    public String id;
    public static String BASE_URL = "https://api.themoviedb.org";
    public static String API_KEY = "api_key";
    public static String API = "f96a881187713e61d7b05dd581ac8c4f";
    ArrayList<String> sendReview = new ArrayList<String>();
    AppDataBase dataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        addFav = (ImageView) findViewById(R.id.Img_Favorite);
        txtReleaseDate = (TextView) findViewById(R.id.txt_releaseDate);
        txtOriginalLanguage = (TextView) findViewById(R.id.txt_original_language);
        txtVoteAvarage = (TextView) findViewById(R.id.txt_vote_avarage);
        txtOriginalTitle = (TextView) findViewById(R.id.txt_original_title);
        backdropImg = (ImageView) findViewById(R.id.backdrop_img);
        txtDescription = (TextView) findViewById(R.id.description);
        imgReview = (ImageView) findViewById(R.id.Img_review);
        Intent receieved = getIntent();
        if (receieved.hasExtra("sendObject"))
            readyToDisplay = (Movie) receieved.getSerializableExtra("sendObject");

        dataBase = AppDataBase.getInstance(getApplicationContext());

        txtOriginalLanguage.setText(readyToDisplay.getOriginal_language());
        txtVoteAvarage.setText(readyToDisplay.getVote_average());
        txtOriginalTitle.setText(readyToDisplay.getOriginal_title());
        txtDescription.setText(readyToDisplay.getOverview());
        txtReleaseDate.setText(readyToDisplay.getRelease_date());


        id = readyToDisplay.getID();
        System.out.println("lets see " + id);

        URL url_review = null;
        try {
            url_review = new URL("https://api.themoviedb.org/3/movie/439079/reviews?api_key=f96a881187713e61d7b05dd581ac8c4f");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        new connect2().execute(url_review);


        if (readyToDisplay.backdrop_path == null)
            backdropImg.setImageResource(R.drawable.ic_launcher_foreground);

        if (readyToDisplay.getBackdrop_path() != null)
            Picasso.get().load(readyToDisplay.getBackdrop_path()).into(backdropImg);

        try {
            URL url = NetworkUtils.buildUrl(id);

            new connect().execute(url);
        } catch (Exception e) {
            e.printStackTrace();
        }


        imgReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailsActivity.this, Review.class);
                intent.putExtra("READY_REVIEW", sendReview);
                startActivity(intent);


            }
        });


        addFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppExecutor.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {

                        if (!ifExists) {
                            dataBase.getMovieDao().Insert(readyToDisplay);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    addFav.setColorFilter(getResources().getColor(R.color.favorite));


                                }
                            });
                            System.out.println("Added Movie " + readyToDisplay.getID());
                        } else {
                            dataBase.getMovieDao().Delete(readyToDisplay);
                            System.out.println("delete the movie " + readyToDisplay.getID());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    addFav.setColorFilter(getResources().getColor(R.color.notFavorite));

                                }
                            });


                        }

                    }

                });


            }
        });


        setupViewModel();

    }


    //---------------------

    private void setupViewModel() {

        MoviewDetaisViewModelFactory factory = new MoviewDetaisViewModelFactory(dataBase, readyToDisplay.getID());
        MovieDetailsViewModel viewModel = ViewModelProviders.of(this, factory).get(MovieDetailsViewModel.class);


        viewModel.getCourse().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {

                if (movie != null) {
                    ifExists = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            addFav.setColorFilter(getResources().getColor(R.color.favorite));

                        }
                    });


                } else {
                    ifExists = false;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addFav.setColorFilter(getResources().getColor(R.color.notFavorite));

                        }
                    });

                }
            }
        });

    }


    //---------

    //--


    public class connect extends AsyncTask<URL, Void, String> implements Serializable {

        URL result_url = null;


        @Override
        protected String doInBackground(URL... params) {
            result_url = params[0];
            String Results = null;
            try {
                Results = NetworkUtils.getResponseFromHttpUrl(result_url);


            } catch (IOException e) {
                e.printStackTrace();
            }
            return Results;
        } //-- end of background



        @Override
        protected void onPostExecute(String Results) {

            if (Results != null && !Results.equals("")) {
                try {


                    KEY = ParsingJsonTrailer.parse(Results);
                    System.out.println("done key " + KEY);

                    buildUrlYoutube(KEY);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } //-- end of post excecute


    } //----end of connect


    ///-- connect2


    /////////connect2
    public class connect2 extends AsyncTask<URL, Void, String> implements Serializable {

        URL result_url = null;


        @Override
        protected String doInBackground(URL... params) {
            result_url = params[0];
            String Results = null;
            try {
                Results = NetworkUtils.getResponseFromHttpUrl(result_url);


            } catch (IOException e) {
                e.printStackTrace();
            }
            return Results;
        } //-- end of background


        @Override
        protected void onPostExecute(String Results) {
            if (Results != null && !Results.equals("")) {
                try {

                    sendReview = ParsingJsonReview.parse(Results);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } //-- end of post excecute


    }


    //--- populate the arraylist


    //--


    //--


    public void buildUrlYoutube(ArrayList<String> keys) {


        ReadyBuiltLinkYouTube = new ArrayList<Uri>();

        for (int i = 0; i < keys.size(); i++) {
            Uri webpage = Uri.parse("https://www.youtube.com/watch").buildUpon()
                    .appendQueryParameter("v", keys.get(i))
                    .build();

            ReadyBuiltLinkYouTube.add(webpage);

        }

        BuildDynamicButtonAndAssignUri(ReadyBuiltLinkYouTube);


    }


    public void BuildDynamicButtonAndAssignUri(ArrayList<Uri> readyUri) {
        String name = "Trailer  ";
        String nameNum;
        int numOfTrailer = readyUri.size();

        ArrayList<String> names = new ArrayList<String>();

        for (int i = 0; i < numOfTrailer; i++) {
            nameNum = name.concat((i + 1) + "");
            names.add(nameNum);


        }


        Uri uri;

        parentOfTrailer = (LinearLayout) findViewById(R.id.parentForReviews);
        Drawable dw = getApplicationContext().getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp);

        for (int i = 0; i < numOfTrailer; i++) {
            dynamicButton = new Button(getApplicationContext());

            dynamicButton.setId(i + 1);
            dynamicButton.setCompoundDrawablesWithIntrinsicBounds(null, null, dw, null);
            dynamicButton.setText(names.get(i));

            parentOfTrailer.addView(dynamicButton);

            uri = ReadyBuiltLinkYouTube.get(i);

            final Uri finalUri = uri;
            dynamicButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(Intent.ACTION_VIEW, finalUri);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);

                    }

                }


            });

        }


    }


}

