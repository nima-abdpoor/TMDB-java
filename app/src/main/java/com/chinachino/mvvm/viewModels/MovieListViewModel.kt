package com.chinachino.mvvm.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.chinachino.mvvm.models.Result
import com.chinachino.mvvm.repositories.MovieRepository

class MovieListViewModel : ViewModel() {
    private val movieRepository: MovieRepository = MovieRepository
    var isMovieRetrieved = false
    fun searchNextPage() {
        movieRepository.searchNextQuery()
    }

    val movies: LiveData<List<Result>>
        get() = movieRepository.movies

    fun searchMovieAPI(query: String?, page: Int) {
        movieRepository.searchMovieAPI(query, page)
    }

}