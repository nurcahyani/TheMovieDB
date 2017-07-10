package com.udacity.nyur.themoviedb;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.udacity.nyur.themoviedb.adapter.GridViewAdapter;
import com.udacity.nyur.themoviedb.entities.Movie;
import com.udacity.nyur.themoviedb.utilities.MovieJsonUtils;
import com.udacity.nyur.themoviedb.utilities.NetworkUtils;
import com.udacity.nyur.themoviedb.utilities.Util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = MainActivity.class.getSimpleName();
    final String SORT_BY_POPULARITY="SORT_BY_POPULARITY";
    final String SORT_BY_TOPRATED="SORT_BY_TOPRATED";
    private static String API_KEY = "";

    private ProgressBar mProgressBar;
    private GridView mGridListMovie;
    private GridViewAdapter mGridAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.pb_home);
        mGridListMovie = (GridView) findViewById(R.id.gv_home);


        try {
            String x = Util.getProperty("MY_API_KEY",getApplicationContext());
            Log.i(TAG, "onCreate: "+x);
            API_KEY = "?api_key="+x;
            Log.i(TAG, "onCreate: "+API_KEY);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new FetchMovieTask().execute(SORT_BY_POPULARITY);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, ArrayList<Movie>> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            ArrayList<Movie> movieData;

            try {
                String option = params[0];
                URL movieRequestUrl = NetworkUtils.buildUrl(option,API_KEY);

                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(movieRequestUrl);
                movieData =new MovieJsonUtils().getSimpleListFromJson(jsonMovieResponse);

                Log.i(TAG, "doInBackground: "+movieData.get(0).getPoster_path());

                return movieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(final ArrayList<Movie> movieData) {
            mProgressBar.setVisibility(View.INVISIBLE);

            if (movieData != null) {

                mGridAdapter = new GridViewAdapter(MainActivity.this, R.layout.layout_item, movieData);
                mGridListMovie.setAdapter(mGridAdapter);

                mGridListMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Movie movie = movieData.get(position);
                        float a= movie.getVote_average();
                        String b = Float.toString(a) +"/ 10.0";
                        Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                        intent.putExtra("poster_path",movie.getPoster_path());
                        intent.putExtra("backdrop_path",movie.getBackdrop_path());
                        intent.putExtra("year",movie.getRelease_date());
                        intent.putExtra("rating",b);
                        intent.putExtra("release","Release Date : "+movie.getRelease_date());
                        intent.putExtra("synopsis",movie.getOverview());
                        intent.putExtra("title",movie.getTitle());
                        intent.putExtra("id",movie.getId());

                        startActivity(intent);
                    }
                });
            }else {
                Toast.makeText(MainActivity.this, "Check Your Connection!", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== R.id.action_sortnypopularity){
            new FetchMovieTask().execute(SORT_BY_POPULARITY);
            return true;
        }else
        if (item.getItemId()== R.id.action_sortbytoprated){
            new FetchMovieTask().execute(SORT_BY_TOPRATED);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
