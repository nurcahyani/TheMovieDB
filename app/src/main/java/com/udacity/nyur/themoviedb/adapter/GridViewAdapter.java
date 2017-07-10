package com.udacity.nyur.themoviedb.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.nyur.themoviedb.R;
import com.udacity.nyur.themoviedb.entities.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Desy on 7/9/2017.
 */

public class GridViewAdapter extends ArrayAdapter<Movie> {

    private Context mContext;
    private int layoutResourceId;
    private List<Movie> mMovieData = new ArrayList<Movie>();



    public GridViewAdapter(Context mContext, int layoutResourceId, ArrayList<Movie> mMovieData) {
        super(mContext, layoutResourceId, mMovieData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mMovieData = mMovieData;
    }

    public void setGridData(ArrayList<Movie> mMovieData) {
        this.mMovieData = mMovieData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Movie item = mMovieData.get(position);
        holder.titleTextView.setText(Html.fromHtml(item.getTitle()));

        Picasso.with(mContext)
                .load(item.getPoster_path())
                .fit()
                .tag(mContext)
                .into(holder.imageView);

        return row;
    }



    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.tv_home_item);
            imageView = (ImageView) itemView.findViewById(R.id.iv_home_item);
        }


    }







}
