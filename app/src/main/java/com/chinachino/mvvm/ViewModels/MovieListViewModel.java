package com.chinachino.mvvm.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.chinachino.mvvm.Repositories.MovieRepository;
import com.chinachino.mvvm.models.Result;

import java.util.List;

public class MovieListViewModel extends ViewModel {
    private MovieRepository movieRepository;

    public void SearchNextPage(){
        movieRepository.SearchNextQuery();
    }

    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }
    public LiveData<List<Result>> getMovies() {
        return movieRepository.getMovies();
    }
    public void SearchMovieAPI(String query,int page){
        movieRepository.SearchMovieAPI(query,page);
    }
}
