package com.demoapp.ViewModel;


import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.demoapp.APIUtils.NetworkState;
import com.demoapp.Retrofit.ServiceGenerator;
import com.demoapp.LiveDataSource.MoviesDataSource;
import com.demoapp.LiveDataSource.MoviesDataSourceFactory;
import com.demoapp.Model.MovieDetails;
import com.demoapp.Retrofit.ApiRequest;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MoviesDataViewModel extends ViewModel {
    private static final String TAG = "MoviesDataViewModel";
    private LiveData<PagedList<MovieDetails>> moviesDataList;
    private LiveData<NetworkState> networkStateLiveData;
    private Executor executor;
    private LiveData<MoviesDataSource> dataSource;


    public MoviesDataViewModel() {
        Log.d(TAG, "MoviesDataViewModel: ");
        executor = Executors.newFixedThreadPool(5);
        ApiRequest webService = ServiceGenerator.createService(ApiRequest.class);
        MoviesDataSourceFactory factory = new MoviesDataSourceFactory(executor,webService);
        dataSource =  factory.getMutableLiveData();

        networkStateLiveData = Transformations.switchMap(factory.getMutableLiveData(), new Function<MoviesDataSource, LiveData<NetworkState>>() {
            @Override
            public LiveData<NetworkState> apply(MoviesDataSource source) {
                Log.d(TAG, "apply: network change");
                return source.getNetworkState();
            }
        });

        PagedList.Config pageConfig = (new PagedList.Config.Builder())
                                                .setEnablePlaceholders(true)
                                                .setInitialLoadSizeHint(10)
                                                .setPageSize(20).build();

        moviesDataList = (new LivePagedListBuilder<Long, MovieDetails>(factory,pageConfig))
                                                    .setFetchExecutor(executor)
                                                    .build();

    }

    public LiveData<PagedList<MovieDetails>> getMoviesDataList() {
        return moviesDataList;
    }

    public LiveData<NetworkState> getNetworkStateLiveData() {
        return networkStateLiveData;
    }
}

