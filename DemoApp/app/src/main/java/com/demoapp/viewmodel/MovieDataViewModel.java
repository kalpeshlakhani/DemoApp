package com.demoapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.demoapp.constants.AppConstant;
import com.demoapp.repository.MovieDataRepository;
import com.demoapp.response.MovieDataResponse;

public class MovieDataViewModel extends AndroidViewModel {

    private MovieDataRepository movieDataRepository;
    private LiveData<MovieDataResponse> movieResponseLiveData;

    public MovieDataViewModel(@NonNull Application application) {
        super(application);

        movieDataRepository = new MovieDataRepository();
        this.movieResponseLiveData = movieDataRepository.getMovieArticles(AppConstant.API_KEY,1);
    }

    public LiveData<MovieDataResponse> getMovieResponseLiveData() {
        return movieResponseLiveData;
    }
}
