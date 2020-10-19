package com.demoapp.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.demoapp.response.MovieDataResponse;
import com.demoapp.retrofit.APIRequestClient;
import com.demoapp.retrofit.ApiRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataRepository {
    private static final String TAG = MovieDataRepository.class.getSimpleName();
    private ApiRequest apiRequest;

    public MovieDataRepository() {
        apiRequest = APIRequestClient.getRetrofitClientInstance().create(ApiRequest.class);
    }

    public LiveData<MovieDataResponse> getMovieArticles(String key, int page) {
        final MutableLiveData<MovieDataResponse> data = new MutableLiveData<>();
        apiRequest.getMovieList(key, page)
                .enqueue(new Callback<MovieDataResponse>() {
                    @Override
                    public void onResponse(Call<MovieDataResponse> call, Response<MovieDataResponse> response) {
                        Log.d(TAG, "onResponse response:: " + response);

                        if (response.body() != null) {
                            data.setValue(response.body());

                        }
                    }

                    @Override
                    public void onFailure(Call<MovieDataResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
