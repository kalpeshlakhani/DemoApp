package com.demoapp.liveDataSource;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.demoapp.apiUtils.JSONParser;
import com.demoapp.retrofit.ServiceGenerator;
import com.demoapp.model.MovieDetails;
import com.demoapp.retrofit.ApiRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesDataSource extends PageKeyedDataSource<Long, MovieDetails> {
    private static final String TAG = "MoviesDataDataSou";
    private ApiRequest apiWebService;

    public MoviesDataSource(ApiRequest webService) {
        apiWebService = webService;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params, @NonNull final LoadInitialCallback<Long, MovieDetails> callback) {
        Log.d(TAG, "loadInitial: ");
        apiWebService.getMovieList(ServiceGenerator.API_DEFAULT_PAGE_KEY).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String responseString;
                List<MovieDetails> moviesList;
                if (response.isSuccessful() && response.code() == 200) {
                    try {
                        responseString = response.body().string();
                        moviesList = JSONParser.getMovieList(responseString);
                        Log.e("moviesList----","moviesList---"+moviesList.size());
                        callback.onResult(moviesList, null, (long) 2);

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e(TAG, "onResponse error " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String errorMessage = t.getMessage();
                Log.e(TAG, "onFailure: " + errorMessage);
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params, @NonNull LoadCallback<Long, MovieDetails> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params, @NonNull final LoadCallback<Long, MovieDetails> callback) {
        apiWebService.getMovieList(params.key).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                JSONObject responseJson;
                String responseString;
                List<MovieDetails> moviesList;
                Long nextKey;

                if (response.isSuccessful() && response.code() == 200) {
                    try {
                        responseString = response.body().string();
                        moviesList = JSONParser.getMovieList(responseString);
                        responseJson = new JSONObject(responseString);
                        nextKey = (params.key == responseJson.getInt("total_pages")) ? null : params.key + 1;
                        callback.onResult(moviesList, nextKey);

                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e(TAG, "onResponse error " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String errorMessage = t.getMessage();
                Log.e(TAG, "onFailure: " + errorMessage);
            }
        });
    }


}

