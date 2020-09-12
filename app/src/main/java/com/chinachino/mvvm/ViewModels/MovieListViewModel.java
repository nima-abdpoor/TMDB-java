package com.chinachino.mvvm.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.chinachino.mvvm.Repositories.MovieRepository;
import com.chinachino.mvvm.models.Result;

import java.util.List;

public class MovieListViewModel extends ViewModel {
    private MovieRepository movieRepository;

    public MovieListViewModel() {
        movieRepository = MovieRepository.getInstance();
    }
    public MutableLiveData<List<Result>> getMovies() {
        return movieRepository.getMovies();
    }
    public void SearchMovieAPI(String query){
        movieRepository.SearchMovieAPI(query);
    }
}
