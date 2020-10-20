package com.demoapp.liveDataSource;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.demoapp.retrofit.ApiRequest;

public class MoviesDataSourceFactory extends DataSource.Factory {
    private static final String TAG = "MoviesInTheaterDataSour";
    MoviesDataSource moviesDataSource;
    MutableLiveData<MoviesDataSource> mutableLiveData;
    ApiRequest webService;

    public MoviesDataSourceFactory(ApiRequest webService) {
        this.webService = webService;
        mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        Log.d(TAG, "create: ");
        moviesDataSource = new MoviesDataSource(webService);
        mutableLiveData.postValue(moviesDataSource);
        return moviesDataSource;
    }
}
