package com.chinachino.mvvm.Repositories;

import androidx.lifecycle.LiveData;

import com.chinachino.mvvm.Requests.MovieAPIClient;
import com.chinachino.mvvm.models.Details;

public class MovieDetailsRepository {
    private static MovieDetailsRepository instance;
    private MovieAPIClient movieAPIClient;

    public static MovieDetailsRepository getInstance() {
        if (instance == null) {
            instance = new MovieDetailsRepository();
        }
        return instance;
    }

    private MovieDetailsRepository() {
        movieAPIClient = MovieAPIClient.getInstance();
    }


    public LiveData<Details> getMovieDetails(){
        return movieAPIClient.getMovieDetails();
    }

    public void SearchMovieDetails(int movieID){
        movieAPIClient.SearchMovieID(movieID);
    }
}
