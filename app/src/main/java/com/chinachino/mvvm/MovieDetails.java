package com.chinachino.mvvm;

import android.os.Bundle;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class MovieDetails extends BaseActivity {
    //UI
    AppCompatImageView imageView;
    TextView title,socialRank,overview;
    ScrollView scrollView;

    private String TAG = "MovieDetails";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        initViewItems();
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
            int movieID = getIntent().getIntExtra("MovieID",0);
            Log.d(TAG, "getIncomingIntent: "+movieID);
        }
    }
}
