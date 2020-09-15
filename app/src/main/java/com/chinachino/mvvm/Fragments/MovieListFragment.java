package com.chinachino.mvvm.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chinachino.mvvm.Adapters.MovieRecyclerAdapter;
import com.chinachino.mvvm.Adapters.OnMovieListener;
import com.chinachino.mvvm.R;
import com.chinachino.mvvm.Testing;
import com.chinachino.mvvm.ViewModels.MovieListViewModel;

import java.util.Random;

import static com.chinachino.mvvm.Utils.Constants.DEFAULT_MOVIE_LIST_NAME;
import static com.chinachino.mvvm.Utils.Constants.DEFAULT_PAGE;


public class MovieListFragment extends Fragment implements OnMovieListener {

    private RecyclerView recyclerView;
    private static final String TAG = "MovieListFragment";
    private MovieListViewModel mviewModel;
    private MovieRecyclerAdapter adapter;

    NavController navController;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mviewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);
        subscribeObservers();
        initSearchView();
    }

    private void initRecyclerView() {
        adapter = new MovieRecyclerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)){
                    mviewModel.SearchNextPage();
                }
            }
        });
    }
    private void initSearchView(){

    }

    public void subscribeObservers() {
        mviewModel.getMovies().observe(this, results -> {
            if (results != null) {
                Testing.Test(results, TAG);
                mviewModel.setMovieRetrieved(true);
                adapter.setResults(results);
            }
            else {
                mviewModel.setMovieRetrieved(false);
            }
        });
        mviewModel.isRequestTimedOut().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean && !mviewModel.isMovieRetrieved()){
                    Log.d(TAG, "onChanged: Connection Timed Out!");
                    adapter.ShowErrorResult();
                }
            }
        });
    }
    private void SearchMovieAPI(String query , int page,boolean onResume) {
        if (onResume){
            Random random = new Random();
            int number = random.nextInt(9);
            Log.d(TAG, "askjdfljsfl: "+number);
            try {
                mviewModel.SearchMovieAPI(DEFAULT_MOVIE_LIST_NAME[number],DEFAULT_PAGE);
            }
            catch (ArrayIndexOutOfBoundsException e){
                SearchMovieAPI("error",DEFAULT_PAGE,false);
                Log.e(TAG, "SearchMovieAPI: searchOnResume", e);
            }
        }
        else {
            mviewModel.SearchMovieAPI(query,page);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_list, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        recyclerView = view.findViewById(R.id.movie_list);
        initRecyclerView();
        final SearchView searchView = view.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.displayLoading();
                SearchMovieAPI(query,1,false);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onMovieClick(int position) {
        int movieID = adapter.getMovieID(position);
        Bundle bundle =new Bundle();
        bundle.putInt("movieID",movieID);
        navController.navigate(R.id.action_movieListFragment_to_movieDetailsFragment,bundle);
    }

    @Override
    public void onCategoryClick(String category) {

    }

    @Override
    public void onResume() {
        super.onResume();
        SearchMovieAPI("life",DEFAULT_PAGE,true);
    }
}