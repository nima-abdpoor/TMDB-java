package com.chinachino.mvvm.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.chinachino.mvvm.models.Details
import com.chinachino.mvvm.repositories.MovieDetailsRepository


class MovieDetailsViewModel : ViewModel() {
    var isMovieRetrieved = false
    private val movieRepository: MovieDetailsRepository = MovieDetailsRepository
    val movieDetails: LiveData<Details>
        get() = movieRepository.movieDetails

    fun searchMovieDetails(movieID: Int) {
        movieRepository.searchMovieDetails(movieID)
    }

}