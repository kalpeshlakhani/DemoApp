package com.demoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demoapp.R;
import com.demoapp.constants.AppConstant;
import com.demoapp.model.MovieDetails;

import java.util.ArrayList;

public class MovieDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    ArrayList<MovieDetails> movieDetailsArrayList;

    public MovieDataAdapter(Context context, ArrayList<MovieDetails> MovieDetailsArrayList) {
        this.context = context;
        this.movieDetailsArrayList = MovieDetailsArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        RecyclerView.ViewHolder vh;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid_image_two_line, parent, false);
        vh = new OriginalViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        MovieDetails MovieDetails = movieDetailsArrayList.get(i);
        if (holder instanceof OriginalViewHolder) {
            OriginalViewHolder viewHolder = (OriginalViewHolder) holder;
            viewHolder.tvMovieName.setText(MovieDetails.getTitle());

            Glide.with(context)
                    .load(AppConstant.IMAGE_BASE_URL + MovieDetails.getPoster_path())
                    .into(viewHolder.ivMovie);
        }
    }

    @Override
    public int getItemCount() {
        return movieDetailsArrayList.size();
    }

    public class OriginalViewHolder extends RecyclerView.ViewHolder {

        //  @BindView(R.id.ivMovie)
        ImageView ivMovie;

        //  @BindView(R.id.tvMovieName)
        TextView tvMovieName;


        public OriginalViewHolder(@NonNull View itemView) {
            super(itemView);

            ivMovie = itemView.findViewById(R.id.ivMovie);
            tvMovieName = itemView.findViewById(R.id.tvMovieName);
            //ButterKnife.bind(context,itemView);
        }
    }
}
