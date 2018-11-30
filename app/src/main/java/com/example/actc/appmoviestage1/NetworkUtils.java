package com.example.actc.appmoviestage1;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

final public class NetworkUtils {
private NetworkUtils(){}

    public static URL buildUrl(String id) throws MalformedURLException {

        Uri builtUri = Uri.parse("https://api.themoviedb.org").buildUpon()
                .path("3/movie/" + id + "/videos")
                .appendQueryParameter("api_key", "f96a881187713e61d7b05dd581ac8c4f")
                .build();


        URL url = null;

        url = new URL(builtUri.toString());

        return url;
    }
//////////////////////--------------


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
