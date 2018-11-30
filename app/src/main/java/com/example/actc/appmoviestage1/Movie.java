package com.example.actc.appmoviestage1;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;


@Entity(tableName = "MovieModel")

public class Movie implements Serializable {
    @PrimaryKey
     @NonNull String ID;


    String vote_count,  video, vote_average, Title,
            popularity, poster_path, original_language,
            original_title, backdrop_path, adult, overview, release_date;


    public Movie(
            String vote_count, String ID, String video, String vote_average, String Title,
            String popularity, String poster_path, String original_language,
            String original_title, String backdrop_path, String adult
            , String overview, String release_date) {

        this.vote_count = vote_count;
        this.ID = ID;
        this.video = video;
        this.vote_average = vote_average;
        this.Title = Title;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.original_title = original_title;
        this.backdrop_path = backdrop_path;
        this.adult = adult;
        this.overview = overview;
        this.release_date = release_date;


    }


@Ignore
    public Movie(
            String vote_count, String video, String vote_average, String Title,
            String popularity, String poster_path, String original_language,
            String original_title, String backdrop_path, String adult
            , String overview, String release_date) {

        this.vote_count = vote_count;
        this.video = video;
        this.vote_average = vote_average;
        this.Title = Title;
        this.popularity = popularity;
        this.poster_path = poster_path;
        this.original_language = original_language;
        this.original_title = original_title;
        this.backdrop_path = backdrop_path;
        this.adult = adult;
        this.overview = overview;
        this.release_date = release_date;


    }



    String getVote_count() {
        return vote_count;
    }

    String getID() {
        return ID;
    }

    String getVideo() {
        return video;
    }

    String getVote_average() {
        return vote_average;
    }

    String getTitle() {
        return Title;
    }

    String getPoster_path() {
        return poster_path;
    }

    String getOriginal_language() {
        return original_language;
    }

    String getOriginal_title() {
        return original_title;
    }

    String getBackdrop_path() {
        return backdrop_path;
    }

    String getAdult() {
        return adult;
    }

    String getOverview() {
        return overview;
    }

    String getRelease_date() {
        return release_date;
    }

    String getPopularity() {
        return popularity;
    }

    public void setTitle(String t) {
        Title = t;
    }




}
