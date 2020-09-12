package com.chinachino.mvvm;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.chinachino.mvvm.Utils.Testing;
import com.chinachino.mvvm.ViewModels.MovieListViewModel;

public class MovieListActivity extends BaseActivity {
    private static final String TAG = "RecipeListActivity";
    private MovieListViewModel mviewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_recipe_list);
        mviewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);
        subscribeObservers();
        TestSearRetrofit("chicken");

    }
public void subscribeObservers() {
        mviewModel.getMovies().observe(this, results -> {
            if (results !=null)
                Testing.Test(results,TAG);
        });
}
    private void TestSearRetrofit(String query) {
        SearchMovieAPI(query);
    }

    private void SearchMovieAPI(String query){
        mviewModel.SearchMovieAPI(query);
    }
}