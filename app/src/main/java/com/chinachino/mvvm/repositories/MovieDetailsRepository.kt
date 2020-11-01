package com.chinachino.mvvm.repositories

import androidx.lifecycle.LiveData
import com.chinachino.mvvm.requests.MovieAPIClient
import com.chinachino.mvvm.models.Details

//
class MovieDetailsRepository private constructor() {
    private val movieAPIClient: MovieAPIClient = MovieAPIClient()
    val isRequestTimeOut: LiveData<Boolean>
        get() = movieAPIClient.isRequestTimeOut()
    val movieDetails: LiveData<Details?>
        get() = movieAPIClient.movieDetails

    fun searchMovieDetails(movieID: Int) {
        movieAPIClient.searchMovieID(movieID)
    }

    companion object {
        var instance: MovieDetailsRepository? = null
            get() {
                if (field == null) {
                    field = MovieDetailsRepository()
                }
                return field
            }
            private set
    }

}