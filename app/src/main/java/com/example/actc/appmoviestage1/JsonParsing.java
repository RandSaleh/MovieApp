package com.example.actc.appmoviestage1;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.PortUnreachableException;
import java.util.ArrayList;

public class JsonParsing {
    public static final String RESULT = "results";
    public static final String VOTE_COUNT = "vote_count";
    public static final String ID = "id";
    public static final String VIDEO = "video";
    public static final String VOTE_AVG = "vote_average";
    public static final String TITLE = "title";
    public static final String POPULARITY = "popularity";
    public static final String POSTER_PATH = "poster_path";
    public static final String ORIGINAL_LANGUAGE = "original_language";
    public static final String ORIGINAL_TITLE = "original_title";
    public static final String BACKDROP_PATH = "backdrop_path";
    public static final String ADULT = "adult";
    public static final String OVERVIEW = "overview";
    public static final String RELEASE_DATE = "release_date";

    public static ArrayList<Movie> parse(String httpResponse) throws Exception {
        ArrayList<Movie> list_movie = new ArrayList<Movie>(); //// final result

        String ready_URL_poster;
        String ready_URL_backdrop;
        Movie temp_movie = null;

        JSONObject Starter = new JSONObject(httpResponse);


        JSONArray result_Array = Starter.getJSONArray(RESULT);


        for (int i = 0; i < 20; i++) {
            JSONObject m1 = result_Array.getJSONObject(i);
            String vote_count = m1.getString(VOTE_COUNT);
            String id = m1.getString(ID);
            String video = m1.getString(VIDEO);
            String vote_avarage = m1.getString(VOTE_AVG);
            String title = m1.getString(TITLE);
            String popularity = m1.getString(POPULARITY);
            String poster_path = m1.getString(POSTER_PATH);
            String original_language = m1.getString(ORIGINAL_LANGUAGE);
            String original_title = m1.getString(ORIGINAL_TITLE);
            String backdrop_path = m1.getString(BACKDROP_PATH);
            String adult = m1.getString(ADULT);
            String overview = m1.getString(OVERVIEW);
            String release_date = m1.getString(RELEASE_DATE);

            ready_URL_poster = Relativize(poster_path);
            ready_URL_backdrop = Relativize(backdrop_path);
            temp_movie = new Movie(vote_count, id, video, vote_avarage, title, popularity, ready_URL_poster, original_language, original_title, ready_URL_backdrop, adult, overview, release_date);


            list_movie.add(temp_movie);
        }

        return list_movie;

    }

    public static String Relativize(String relative) {
        String BASE_URL_PIC = "https://image.tmdb.org/t/p/original";
        String temp_url = BASE_URL_PIC.concat(relative);
        return temp_url;
    }


}
