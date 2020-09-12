package com.chinachino.mvvm;

import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.chinachino.mvvm.Requests.ServiceGenerator;
import com.chinachino.mvvm.Requests.TheMovieDataBaseAPI;
import com.chinachino.mvvm.ViewModels.MovieListViewModel;
import com.chinachino.mvvm.models.Example;
import com.chinachino.mvvm.models.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.chinachino.mvvm.Utils.Constants.API_KEY;
import static com.chinachino.mvvm.Utils.Constants.DEFAULT_ADULT;
import static com.chinachino.mvvm.Utils.Constants.DEFAULT_LANGUAGE;
import static com.chinachino.mvvm.Utils.Constants.DEFAULT_PAGE;

public class MovieListActivity extends BaseActivity {
    private static final String TAG = "RecipeListActivity";
    private MovieListViewModel mviewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_recipe_list);
        mviewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);
        subscribeObservers();
    }
public void subscribeObservers() {
        mviewModel.getMovies().observe(this, results -> {

        });
}

    private void TestSearRetrofit(String query) {
        TheMovieDataBaseAPI theMoviedbAPI = ServiceGenerator.GetMovies();
        theMoviedbAPI.SearchResponse(API_KEY,DEFAULT_LANGUAGE,query,DEFAULT_PAGE,DEFAULT_ADULT)
                .enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
                        if (response.isSuccessful()) {
                            Example example = response.body();
                            Log.d(TAG, "onResponse: "+example.getTotalPages());
                            for (Result result:example.getResults()){
                                Log.d(TAG, "onResponse: result"+result.getTitle());
                            }
                        } else {
                            try {
                                Log.d(TAG, "onResponse: error" + response.errorBody().toString());
                            } catch (Exception e) {
                                Log.e(TAG, "onResponse: error", e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {

                    }
                });
    }
}