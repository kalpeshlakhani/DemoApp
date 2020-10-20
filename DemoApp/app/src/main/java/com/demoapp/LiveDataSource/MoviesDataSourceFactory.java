package com.demoapp.LiveDataSource;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import com.demoapp.Retrofit.ApiRequest;
import java.util.concurrent.Executor;

public class MoviesDataSourceFactory extends DataSource.Factory {
    private static final String TAG = "MoviesInTheaterDataSour";
    MoviesDataSource moviesDataSource;
    MutableLiveData<MoviesDataSource> mutableLiveData;
    Executor executor;
    ApiRequest webService;

    public MoviesDataSourceFactory(Executor executor, ApiRequest webService) {
        
      this.executor = executor;
      this.webService = webService;
      mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        Log.d(TAG, "create: ");
                moviesDataSource = new MoviesDataSource(executor,webService);
                mutableLiveData.postValue(moviesDataSource);
                return moviesDataSource;
    }

    public MutableLiveData<MoviesDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
