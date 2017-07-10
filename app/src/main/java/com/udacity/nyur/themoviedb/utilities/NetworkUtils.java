package com.udacity.nyur.themoviedb.utilities;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Desy on 7/9/2017.
 */

public class NetworkUtils extends Activity{
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String TOPRANKED_MOVIE_URL =
            "http://api.themoviedb.org/3/movie/top_rated";

    private static final String POPULAR_MOVIE_URL =
            "http://api.themoviedb.org/3/movie/popular";

    private static String MOVIE_BASE_URL="";

    private static final String format = "json";
    private static final String SORT_BY_POPULARITY="SORT_BY_POPULARITY";
    private static final String SORT_BY_TOPRATED="SORT_BY_TOPRATED";




    public static URL buildUrl(String option,String API_KEY) {


        if (option.equalsIgnoreCase(SORT_BY_POPULARITY)){
            MOVIE_BASE_URL = POPULAR_MOVIE_URL+API_KEY;
        }else{
            MOVIE_BASE_URL = TOPRANKED_MOVIE_URL+API_KEY;
        }
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * Builds the URL used to talk to the weather server using latitude and longitude of a
     * location.
     *
     * @param lat The latitude of the location
     * @param lon The longitude of the location
     * @return The Url to use to query the weather server.
     */
    public static URL buildUrl(Double lat, Double lon) {
        /** This will be implemented in a future lesson **/
        return null;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
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
