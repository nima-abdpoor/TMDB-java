package com.chinachino.mvvm;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chinachino.mvvm.Adapters.MovieRecyclerAdapter;
import com.chinachino.mvvm.Adapters.OnMovieListener;
import com.chinachino.mvvm.ViewModels.MovieListViewModel;

public class MovieListActivity extends BaseActivity implements OnMovieListener {
    private RecyclerView recyclerView;
    private static final String TAG = "RecipeListActivity";
    private MovieListViewModel mviewModel;
    private MovieRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_movie_list);
        recyclerView = findViewById(R.id.movie_list);
        mviewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);
        initRecyclerView();
        subscribeObservers();
        SearchMovieAPI("life");
        initSearchView();

    }

    private void initRecyclerView() {
        adapter = new MovieRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void subscribeObservers() {
        mviewModel.getMovies().observe(this, results -> {
            if (results != null)
                Testing.Test(results, TAG);
            adapter.setResults(results);
        });
    }

   private void initSearchView(){
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.displayLoading();
                SearchMovieAPI(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
   }

    private void SearchMovieAPI(String query) {
        mviewModel.SearchMovieAPI(query);
    }

    @Override
    public void onMovieClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {

    }
}