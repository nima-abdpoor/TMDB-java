package com.chinachino.mvvm.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.chinachino.mvvm.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView title, description, socialRank;
    AppCompatImageView image;
    OnMovieListener onMovieListener;

    public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);
        title = itemView.findViewById(R.id.movie_title);
        description = itemView.findViewById(R.id.movie_description);
        socialRank = itemView.findViewById(R.id.movie_social_rank);
        image = itemView.findViewById(R.id.movie_image);
        this.onMovieListener = onMovieListener;
        itemView.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        onMovieListener.onMovieClick(getAdapterPosition());
    }
}
