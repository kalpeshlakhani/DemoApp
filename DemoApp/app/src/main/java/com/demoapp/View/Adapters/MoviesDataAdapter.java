package com.demoapp.view.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demoapp.apiUtils.Image;
import com.demoapp.model.MovieDetails;
import com.demoapp.R;


public class MoviesDataAdapter extends PagedListAdapter<MovieDetails, RecyclerView.ViewHolder> {
    private static final String TAG = "MoviesDataAdapter";
    private Context mContext;

    public MoviesDataAdapter(Context context) {
        super(MovieDetails.DIFF_CALL);
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        Log.e("getItemCount--", "getItemCount--" + getItemCount());
        return getItemCount();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        view = inflater.inflate(R.layout.item_grid_image_two_line, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MovieViewHolder) {
            MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
            MovieDetails movie = getItem(position);
            movieViewHolder.bind(movie, mContext);
        }
    }

    private static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovieName;
        ImageView ivMovie;

        public MovieViewHolder(View itemView) {
            super(itemView);
            tvMovieName = itemView.findViewById(R.id.tvMovieName);
            ivMovie = itemView.findViewById(R.id.ivMovie);
        }

        public void bind(MovieDetails movie, Context context) {
            tvMovieName.setText(movie.getTitle());
            Image image = new Image(movie.getPoster_path());
            Glide.with(context).load(image.getLowQualityImagePath()).into(ivMovie);
        }
    }

}
