package com.chinachino.mvvm;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chinachino.mvvm.Adapters.MovieRecyclerAdapter;
import com.chinachino.mvvm.Adapters.OnMovieListener;
import com.chinachino.mvvm.ViewModels.MovieListViewModel;

import static com.chinachino.mvvm.Utils.Constants.DEFAULT_PAGE;

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
        SearchMovieAPI("life",DEFAULT_PAGE);
        initSearchView();

    }

    private void initRecyclerView() {
        adapter = new MovieRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)){
                    mviewModel.SearchNextPage();
                }
            }
        });
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
                SearchMovieAPI(query,1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
   }

    private void SearchMovieAPI(String query,int page) {
        mviewModel.SearchMovieAPI(query,page);
    }

    @Override
    public void onMovieClick(int position) {
        Integer integer = adapter.getMovieID(position);
        Intent intent = new Intent(this,MovieDetails.class);
        intent.putExtra("MovieID",integer);
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {

    }
}