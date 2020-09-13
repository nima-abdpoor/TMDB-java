package com.chinachino.mvvm.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chinachino.mvvm.BaseActivity;
import com.chinachino.mvvm.R;
import com.chinachino.mvvm.ViewModels.MovieDetailsViewModel;
import com.chinachino.mvvm.models.Details;
import com.chinachino.mvvm.models.Genre;

import static com.chinachino.mvvm.Utils.Constants.IMAGE_BASE_URL;

public class MovieDetailsActivity extends BaseActivity {
    //UI
    AppCompatImageView imageView;
    TextView title,overview,rank,genres;
    ScrollView scrollView;
    MovieDetailsViewModel viewModel;

    private String TAG = "MovieDetailsActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ShowProgressBar(true);
        verifyingViewItems();
        viewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);
        SubscribeOnObservers();
        getIncomingIntent();
    }

    private void verifyingViewItems() {
        imageView = findViewById(R.id.movie_image_detail);
        title = findViewById(R.id.movie_title_detail);
        overview = findViewById(R.id.overview_title);
        rank = findViewById(R.id.movie_vote);
        scrollView = findViewById(R.id.parent);
        genres = findViewById(R.id.genres);
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
               InitViewItems(details);
            }
            else{
                Log.d(TAG, "onChanged: detail is null");
            }
        });
    }

    private void InitViewItems(Details details) {
        String genre = "genres : ";
        ShowProgressBar(false);
        scrollView.setVisibility(View.VISIBLE);
        title.setText(details.getTitle());
        overview.setText(details.getOverview());
        rank.setText(String.valueOf(details.getVoteAverage()));
        for(Genre s : details.getGenres())
             genre +="\n - " + s.getName();
        genres.setText(genre);
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(IMAGE_BASE_URL + details.getBackdropPath())
                .into(imageView);
    }

    @Override
    public void ShowProgressBar(boolean visibility) {
        super.ShowProgressBar(visibility);
    }
}
