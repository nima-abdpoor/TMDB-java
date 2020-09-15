package com.chinachino.mvvm.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chinachino.mvvm.R;
import com.chinachino.mvvm.ViewModels.MovieDetailsViewModel;
import com.chinachino.mvvm.models.Details;
import com.chinachino.mvvm.models.Genre;

import static com.chinachino.mvvm.Utils.Constants.IMAGE_BASE_URL;


public class MovieDetailsFragment extends Fragment {
    //UI
    AppCompatImageView imageView;
    TextView title,overview,rank,genres;
    ScrollView scrollView;
    MovieDetailsViewModel viewModel;

    private String TAG = "MovieDetailsActivity";

    int movieID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);
        movieID = getArguments().getInt("movieID");
        SearchMovieDetails();
        SubscribeOnObservers();
    }
    public void SearchMovieDetails(){
        viewModel.SearchMovieDetails(movieID);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }
    private void SubscribeOnObservers(){
        Log.d(TAG, "SubscribeOnObservers: ");
        viewModel.getMovieDetails().observe(this, details -> {
            if(details !=null){
                InitViewItems(details);
                viewModel.setMovieRetrieved(true);
            }
            else{
                Log.d(TAG, "onChanged: detail is null");
            }
        });
       viewModel.isRequestTimedOut().observe(this, new Observer<Boolean>() {
           @Override
           public void onChanged(Boolean aBoolean) {
               if(aBoolean && !viewModel.isMovieRetrieved()){
                   Log.d(TAG, "onChanged: Connection Timed Out... ");
                   ShowErrorMessage("ConnectionTimedOut!");
               }
           }
       });
    }
    private void ShowErrorMessage(String error) {
        title.setText(error);
        title.setTextSize(20f);
        overview.setText("");
        rank.setText("");
        genres.setText("");
        Glide.with(this)
                .load(R.drawable.ic_launcher_background)
                .into(imageView);
    }



    private void InitViewItems(Details details) {
        String genre = "genres : ";
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.movie_image_detail);
        title = view.findViewById(R.id.movie_title_detail);
        overview = view.findViewById(R.id.overview_title);
        rank = view.findViewById(R.id.movie_vote);
        scrollView = view.findViewById(R.id.parent);
        genres = view.findViewById(R.id.genres);
    }
}