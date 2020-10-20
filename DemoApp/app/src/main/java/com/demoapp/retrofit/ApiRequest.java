package com.demoapp.retrofit;



import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRequest {

    @GET("movie/popular")
    Call<ResponseBody> getMovieList(@Query("page") long page);

}

