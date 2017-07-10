package com.udacity.nyur.themoviedb.utilities;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.udacity.nyur.themoviedb.entities.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Desy on 7/9/2017.
 */

public class MovieJsonUtils {
    final String OWM_DESCRIPTION = "results";

    public ArrayList<Movie> getSimpleListFromJson(String jsonResult) {
        ArrayList<Movie> data  = new ArrayList<>();
        JSONObject result = null;
        try {
            result = new JSONObject(jsonResult);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                JSONArray data_json = result.getJSONArray(OWM_DESCRIPTION);
                for (int i = 0; i < data_json.length(); i++) {
                    Movie movie = new Movie();
                    JSONObject object = data_json.getJSONObject(i);

                    movie.setId(object.getInt("id"));
                    movie.setPoster_path("http://image.tmdb.org/t/p/w185" + object.getString("poster_path"));
                    movie.setBackdrop_path("http://image.tmdb.org/t/p/w780" + object.getString("backdrop_path"));
                    movie.setRelease_date(object.getString("release_date"));
                    movie.setTitle(object.getString("title"));
                    movie.setOverview(object.getString("overview"));
                    movie.setVote_average(object.getLong("vote_average"));
                    data.add(movie);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return data;
    }
}
