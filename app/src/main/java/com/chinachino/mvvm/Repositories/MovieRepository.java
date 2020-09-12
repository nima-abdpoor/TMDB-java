package com.chinachino.mvvm.Repositories;

import androidx.lifecycle.MutableLiveData;

import com.chinachino.mvvm.Requests.MovieAPIClient;
import com.chinachino.mvvm.models.Result;

import java.util.List;

public class MovieRepository {
    private static MovieRepository instance;
    private MovieAPIClient movieAPIClient;

    public static MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository() {
        movieAPIClient = MovieAPIClient.getInstance();
    }

    public MutableLiveData<List<Result>> getMovies() {
        return movieAPIClient.getMovies();
    }
}
