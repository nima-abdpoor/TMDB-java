package com.chinachino.mvvm.Requests;

import androidx.lifecycle.MutableLiveData;

import com.chinachino.mvvm.AppExecutors;
import com.chinachino.mvvm.Repositories.MovieRepository;
import com.chinachino.mvvm.models.Result;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.chinachino.mvvm.Utils.Constants.TIME_OUT;

public class MovieAPIClient {
    private static MovieAPIClient instance;
    private MutableLiveData<List<Result>> mutableLiveData;

    public static MovieAPIClient getInstance(){
        if (instance == null){
           instance = new MovieAPIClient();
        }
        return instance;
    }
    private MovieAPIClient(){
        mutableLiveData = new MutableLiveData<>();
    }
    public MutableLiveData<List<Result>> getMovies() {
        return mutableLiveData;
    }
    public void SearchMovieAPI(){
        final Future handler = AppExecutors.getInstance().getExecutorService().submit(() -> {

        });
        AppExecutors.getInstance().getExecutorService().schedule(() -> {
            handler.cancel(true);
        },TIME_OUT, TimeUnit.SECONDS);
    }
}
