package com.demoapp.viewModel;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.demoapp.retrofit.ServiceGenerator;
import com.demoapp.liveDataSource.MoviesDataSourceFactory;
import com.demoapp.model.MovieDetails;
import com.demoapp.retrofit.ApiRequest;

public class MoviesDataViewModel extends ViewModel {
    private static final String TAG = "MoviesDataViewModel";
    private LiveData<PagedList<MovieDetails>> moviesDataList;

    public MoviesDataViewModel() {
        Log.d(TAG, "MoviesDataViewModel: ");
        ApiRequest webService = ServiceGenerator.createService(ApiRequest.class);
        MoviesDataSourceFactory factory = new MoviesDataSourceFactory(webService);

        PagedList.Config pageConfig = (new PagedList.Config.Builder())
                                                .setEnablePlaceholders(true)
                                                .setInitialLoadSizeHint(10)
                                                .setPageSize(20).build();

        moviesDataList = (new LivePagedListBuilder<Long, MovieDetails>(factory,pageConfig))
                                                    .build();

    }

    public LiveData<PagedList<MovieDetails>> getMoviesDataList() {
        return moviesDataList;
    }
}

