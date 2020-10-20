package com.demoapp.View.Adapters;

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
import com.demoapp.APIUtils.Image;
import com.demoapp.APIUtils.NetworkState;
import com.demoapp.R;
import com.demoapp.Model.MovieDetails;


public class MoviesDataAdapter extends PagedListAdapter<MovieDetails, RecyclerView.ViewHolder> {
    private static final String TAG = "MoviesInTheaterAdapter";
    public static final int MOVIE_ITEM_VIEW_TYPE = 1;
    public static final int LOAD_ITEM_VIEW_TYPE = 0;
    private Context mContext;
    private NetworkState mNetworkState;

    public MoviesDataAdapter(Context context) {
        super(MovieDetails.DIFF_CALL);
        mContext = context;
    }


    @Override
    public int getItemViewType(int position) {
        Log.e("getItemCount--", "getItemCount--" + getItemCount());
        return (isLoadingData() && position == getItemCount() - 1) ? LOAD_ITEM_VIEW_TYPE : MOVIE_ITEM_VIEW_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == MOVIE_ITEM_VIEW_TYPE) {
            view = inflater.inflate(R.layout.item_grid_image_two_line, parent, false);
            return new MovieViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.load_progress_item, parent, false);
            return new ProgressViewHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MovieViewHolder) {
            MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
            MovieDetails movie = getItem(position);
            movieViewHolder.bind(movie, mContext);
        }
    }

    public void setNetworkState(NetworkState networkState) {
        NetworkState prevState = networkState;
        boolean wasLoading = isLoadingData();
        mNetworkState = networkState;
        boolean willLoad = isLoadingData();
        if (wasLoading != willLoad) {
            if (wasLoading) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        }
    }

    public boolean isLoadingData() {
        return (mNetworkState != null && mNetworkState != NetworkState.LOADED);
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

    private static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }
}
