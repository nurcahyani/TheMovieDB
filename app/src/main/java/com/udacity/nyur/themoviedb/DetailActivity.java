package com.udacity.nyur.themoviedb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView mPosterDetail;
    private TextView mTitleDetail;
    private TextView mRatingDetail;
    private TextView mReleaseDateDetail;
    private TextView mSynopsisDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mPosterDetail = (ImageView) findViewById(R.id.iv_detail_poster);
        mTitleDetail = (TextView) findViewById(R.id.tv_detail_title);
        mRatingDetail = (TextView) findViewById(R.id.tv_detail_rating);
        mReleaseDateDetail = (TextView) findViewById(R.id.tv_detail_release_date);
        mSynopsisDetail = (TextView) findViewById( R.id.tv_detail_synopsis);


        String title = getIntent().getStringExtra("title");
        String rating = getIntent().getStringExtra("rating");
        String releaseDate = getIntent().getStringExtra("release");
        String synopsis = getIntent().getStringExtra("synopsis");
        String image = getIntent().getStringExtra("poster_path");

        if (!title.equals(null)) {
            mTitleDetail.setText(Html.fromHtml(title));
        }
        if (!rating.equals(null)) {
            mRatingDetail.setText(rating);
        }
        if (!releaseDate.equals(null)) {
            mReleaseDateDetail.setText(Html.fromHtml(releaseDate));
        }
        if (!synopsis.equals(null)) {
            mSynopsisDetail.setText(Html.fromHtml(synopsis));
        }

        Picasso.with(this).load(image).into(mPosterDetail);

    }
}
