package com.chinachino.mvvm.requests

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chinachino.mvvm.utils.Constants
import com.chinachino.mvvm.executors.AppExecutors
import com.chinachino.mvvm.models.Details
import com.chinachino.mvvm.models.Example
import com.chinachino.mvvm.models.Result
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.Future

class MovieAPIClient {
    private val listMutableLiveData: MutableLiveData<List<Result?>?> = MutableLiveData()
    private val movieMutableLiveData: MutableLiveData<Details?> = MutableLiveData()
    private val requestTimeOut: MutableLiveData<Boolean> = MutableLiveData()
    private var movieDetailsRunnable: RetrieveMovieDetailsRunnable? = null
    private var retrieveMoviesRunnable: RetrieveMoviesRunnable? = null
    private var scheduler: Scheduler = Scheduler()
    fun isRequestTimeOut(): LiveData<Boolean> {
        return requestTimeOut
    }

    val movies: LiveData<List<Result?>?>
        get() = listMutableLiveData
    val movieDetails: LiveData<Details?>
        get() = movieMutableLiveData

    fun SearchMovieAPI(query: String?, page: Int) {
        requestTimeOut.postValue(false)
        if (retrieveMoviesRunnable != null) {
            retrieveMoviesRunnable = null
        }
        retrieveMoviesRunnable = RetrieveMoviesRunnable(query, page)
        val handler: Future<*> = AppExecutors.executorService.submit(retrieveMoviesRunnable)
        scheduler.schedule(handler, requestTimeOut)
    }

    fun SearchMovieID(movieID: Int) {
        requestTimeOut.postValue(false)
        if (movieDetailsRunnable != null) {
            movieDetailsRunnable = null
        }
        movieDetailsRunnable = RetrieveMovieDetailsRunnable(movieID)
        val handler: Future<*> = AppExecutors.executorService.submit(movieDetailsRunnable)
        scheduler.schedule(handler, requestTimeOut)
    }

    private inner class RetrieveMoviesRunnable(private val query: String?, private val page: Int) : Runnable {
        private var cancelRequest = false
        private val TAG = "MovieAPIClient"
        var results: List<Result>? = null
        override fun run() {
            try {
                val response: Response<*> = getMovies(query, page).execute()
                if (cancelRequest) {
                    return
                }
                if (response.code() == 200) {
                    results = if (page == 1) {
                        (response.body() as Example)?.results
                    } else (response.body() as Example?)?.results
                    listMutableLiveData.postValue(results)
                } else {
                    val error = response.errorBody().toString()
                    Log.d(TAG, "run: $error")
                    listMutableLiveData.postValue(null)
                }
            } catch (e: IOException) {
                Log.d(TAG, "run: " + e.message)
                e.printStackTrace()
                listMutableLiveData.postValue(null)
            }
        }

        fun getMovies(query: String?, page: Int): Call<Example> {
            return ServiceGenerator.getMovies().searchResponse(Constants.API_KEY, Constants.DEFAULT_LANGUAGE, query, page, Constants.DEFAULT_ADULT)
        }

        fun cancelRequest() {
            cancelRequest = true
        }
    }

    private inner class RetrieveMovieDetailsRunnable(private val movieID: Int) : Runnable {
        private var cancelRequest = false
        private val tag = "MovieAPIClient"
        override fun run() {
            try {
                val response: Response<*> = getMovieDetails(movieID).execute()
                if (cancelRequest) {
                    return
                }
                if (response.code() == 200) {
                    val details = response.body() as Details?
                    Log.d(tag, "SubscribeOnObservers " + details!!.overview)
                    movieMutableLiveData.postValue(details)
                } else {
                    val error = response.errorBody().toString()
                    Log.d(tag, "------run: " + response.code() + "|" + response.toString())
                    movieMutableLiveData.postValue(null)
                }
            } catch (e: IOException) {
                Log.d(tag, "run: exception$e")
                e.printStackTrace()
                movieMutableLiveData.postValue(null)
            }
        }

        fun getMovieDetails(movieID: Int): Call<Details> {
            return ServiceGenerator.getMovies().getMovieDetails(movieID, Constants.API_KEY, Constants.DEFAULT_LANGUAGE)
        }

        fun cancelRequest() {
            cancelRequest = true
        }
    }

    fun cancelRequest() {
        if (retrieveMoviesRunnable != null) {
            retrieveMoviesRunnable!!.cancelRequest()
        }
        if (movieDetailsRunnable != null) {
            movieDetailsRunnable!!.cancelRequest()
        }
    }

}