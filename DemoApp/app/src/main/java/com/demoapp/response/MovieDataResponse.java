package com.demoapp.response;

import com.demoapp.model.MovieDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieDataResponse {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("results")
    @Expose
    private List<MovieDetails> movieDetails = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MovieDetails> getMovieDetails() {
        return movieDetails;
    }

    public void setMovieDetails(List<MovieDetails> movieDetails) {
        this.movieDetails = movieDetails;
    }
}
