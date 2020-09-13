package com.chinachino.mvvm;

import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.chinachino.mvvm.ViewModels.MovieDetailsViewModel;
import com.chinachino.mvvm.models.Details;

public class MovieDetailsActivity extends BaseActivity {
    //UI
    AppCompatImageView imageView;
    TextView title,socialRank,overview;
    ScrollView scrollView;
    MovieDetailsViewModel viewModel;

    private String TAG = "MovieDetailsActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initViewItems();
        viewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);
        SubscribeOnObservers();
        getIncomingIntent();
    }

    private void initViewItems() {
        imageView = findViewById(R.id.movie_image_detail);
        title = findViewById(R.id.movie_title_detail);
        overview = findViewById(R.id.overview_title);
        socialRank = findViewById(R.id.movie_social_score);
        scrollView = findViewById(R.id.parent);
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("MovieID")){
            Log.d(TAG, "getIncomingIntent: ");
            int movieID = getIntent().getIntExtra("MovieID",0);
            viewModel.SearchMovieDetails(movieID);
        }
    }
    private void SubscribeOnObservers(){
        Log.d(TAG, "SubscribeOnObservers: ");
        viewModel.getMovieDetails().observe(this, details -> {
            if(details !=null){
                Log.d(TAG, "onChanged: "+details.getOverview());
            }
            else{
                Log.d(TAG, "onChanged: detail is null");
            }
        });
    }
}
