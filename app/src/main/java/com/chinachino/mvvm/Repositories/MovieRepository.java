package com.chinachino.mvvm.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.chinachino.mvvm.Requests.MovieAPIClient;
import com.chinachino.mvvm.models.Details;
import com.chinachino.mvvm.models.Result;

import java.util.List;

public class MovieRepository {
    private static MovieRepository instance;
    private MovieAPIClient movieAPIClient;
    private String query;
    private int pageNumber;

    public static MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }

    private MovieRepository() {
        movieAPIClient = MovieAPIClient.getInstance();
    }

    public LiveData<List<Result>> getMovies() {
        return movieAPIClient.getMovies();
    }
    public LiveData<Details> getMovieDetails(){
        return movieAPIClient.getMovieDetails();
    }
    public void SearchMovieAPI(String query,int page){
        this.query = query;
        this.pageNumber = page;
        movieAPIClient.SearchMovieAPI(query,page);
    }
    public void SearchMovieDetails(int movieID){
        movieAPIClient.SearchMovieID(movieID);
    }
    public void SearchNextQuery(){
        movieAPIClient.SearchMovieAPI(this.query,this.pageNumber+1);
    }
}
