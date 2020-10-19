package com.demoapp.retrofit;


import com.demoapp.response.MovieDataResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRequest {

    @GET("3/movie/popular/")
    Call<MovieDataResponse> getMovieList(
            @Query("api_key") String apiKey,
            @Query("page") int page
            );

}

