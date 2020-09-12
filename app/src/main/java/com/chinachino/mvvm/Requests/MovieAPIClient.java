package com.chinachino.mvvm.Requests;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.chinachino.mvvm.AppExecutors;
import com.chinachino.mvvm.models.Example;
import com.chinachino.mvvm.models.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.chinachino.mvvm.Utils.Constants.API_KEY;
import static com.chinachino.mvvm.Utils.Constants.DEFAULT_ADULT;
import static com.chinachino.mvvm.Utils.Constants.DEFAULT_LANGUAGE;
import static com.chinachino.mvvm.Utils.Constants.DEFAULT_PAGE;
import static com.chinachino.mvvm.Utils.Constants.TIME_OUT;

public class MovieAPIClient {
    RetrieveMoviesRunnable retrieveMoviesRunnable;
    private static MovieAPIClient instance;
    private MutableLiveData<List<Result>> mutableLiveData;

    public static MovieAPIClient getInstance() {
        if (instance == null) {
            instance = new MovieAPIClient();
        }
        return instance;
    }

    private MovieAPIClient() {
        mutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<Result>> getMovies() {
        return mutableLiveData;
    }

    public void SearchMovieAPI(String query) {
        if (retrieveMoviesRunnable !=null){
            retrieveMoviesRunnable = null;
        }
        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query);

        final Future handler = AppExecutors.getInstance().getExecutorService().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().getExecutorService().schedule(() -> {
            handler.cancel(true);
        }, TIME_OUT, TimeUnit.SECONDS);
    }

    private class RetrieveMoviesRunnable implements Runnable {

        private String query;
        private boolean cancelRequest;
        private String TAG = "MovieAPIClient";

        public RetrieveMoviesRunnable(String query) {
            this.query = query;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getMovies(this.query).execute();
                if(cancelRequest){
                    return;
                }
                if (response.code() == 200){
                    List<Result> results = new ArrayList<>(((Example) response.body()).getResults());
                    mutableLiveData.postValue(results);
                }
                else {
                    String error = response.errorBody().toString();
                    Log.d(TAG, "run: "+ error);
                    mutableLiveData.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mutableLiveData.postValue(null);
            }
        }
        public Call<Example> getMovies(String query){
            return ServiceGenerator.GetMovies().SearchResponse(API_KEY, DEFAULT_LANGUAGE
                    , query, DEFAULT_PAGE, DEFAULT_ADULT);
        }
    }
}
