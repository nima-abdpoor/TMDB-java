package com.chinachino.mvvm.repositories

import androidx.lifecycle.LiveData
import com.chinachino.mvvm.requests.MovieAPIClient
import com.chinachino.mvvm.models.Details

//
object MovieDetailsRepository {
    private val movieAPIClient: MovieAPIClient = MovieAPIClient.instance
    val isRequestTimedOut: LiveData<Boolean>
        get() = movieAPIClient.isRequestTimeOut
get() {
        return movieRepository.isRequestTimeOut
    }
    val movieDetails: LiveData<Details>
        get() = movieAPIClient.movieDetails

    fun searchMovieDetails(movieID: Int) {
        movieAPIClient.SearchMovieID(movieID)
    }

}