package com.chinachino.mvvm.repositories

import androidx.lifecycle.LiveData
import com.chinachino.mvvm.requests.MovieAPIClient
import com.chinachino.mvvm.models.Details
import com.chinachino.mvvm.models.Result

object MovieRepository  {
    private val movieAPIClient: MovieAPIClient = MovieAPIClient.instance
    private var query: String? = null
    private var pageNumber = 0
    val movies: LiveData<List<Result>>
        get() = movieAPIClient.movies
    val movieDetails: LiveData<Details>
        get() = movieAPIClient.movieDetails

    fun searchMovieAPI(query: String?, page: Int) {
        this.query = query
        pageNumber = page
        movieAPIClient.SearchMovieAPI(query, page)
    }

    fun searchMovieDetails(movieID: Int) {
        movieAPIClient.SearchMovieID(movieID)
    }

    fun searchNextQuery() {
        movieAPIClient.SearchMovieAPI(query, pageNumber + 1)
    }

    val isRequestTimedOut: LiveData<Boolean>
        get() = movieAPIClient.isRequestTimeOut
get() {
        return movieRepository.isRequestTimeOut
    }


}