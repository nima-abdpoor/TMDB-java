package com.chinachino.mvvm.repositories

import androidx.lifecycle.LiveData
import com.chinachino.mvvm.requests.MovieAPIClient
import com.chinachino.mvvm.models.Details
import com.chinachino.mvvm.models.Result

object MovieRepository  {
    private val movieAPIClient: MovieAPIClient = MovieAPIClient()
    private var  query = ""
    private var pageNumber = 0
    val movies: LiveData<List<Result>>
        get() = movieAPIClient.movies
    val movieDetails: LiveData<Details?>
        get() = movieAPIClient.movieDetails

    fun searchMovieAPI(query: String, page: Int) {
        this.query = query
        pageNumber = page
        movieAPIClient.searchMovieAPI(query, page)
    }

    fun searchMovieDetails(movieID: Int) {
        movieAPIClient.searchMovieID(movieID)
    }

    fun searchNextQuery() {
        movieAPIClient.searchMovieAPI(query, pageNumber + 1)
    }

    val isRequestTimedOut: LiveData<Boolean>
        get() = movieAPIClient.isRequestTimeOut()



}