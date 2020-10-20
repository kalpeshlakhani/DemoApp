package com.demoapp.model;

import androidx.recyclerview.widget.DiffUtil;

public class MovieDetails {
    private int id;
    private String poster_path;
    private String title;

    public MovieDetails(int id, String poster_path, String title) {
        this.id = id;
        this.poster_path = poster_path;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public static final DiffUtil.ItemCallback<MovieDetails> DIFF_CALL = new DiffUtil.ItemCallback<MovieDetails>() {
        @Override
        public boolean areItemsTheSame(MovieDetails oldItem, MovieDetails newItem) {
            return  oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(MovieDetails oldItem, MovieDetails newItem) {
            return  oldItem.id == newItem.id;
        }
    };

}
