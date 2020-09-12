package com.chinachino.mvvm.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chinachino.mvvm.R;
import com.chinachino.mvvm.models.Result;

import java.util.List;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnMovieListener onMovieListener;
    private List<Result> results;

    public MovieRecyclerAdapter(OnMovieListener onMovieListener, List<Result> results) {
        this.onMovieListener = onMovieListener;
        this.results = results;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_list_item,viewGroup,false);
        return new MovieViewHolder(view,onMovieListener);
    }

    public void setResults(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
        Glide.with(viewHolder.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(results.get(i))
                .into(((MovieViewHolder)viewHolder).image);

        ((MovieViewHolder)viewHolder).title.setText(results.get(i).getTitle());
        ((MovieViewHolder)viewHolder).description.setText(results.get(i).getOverview());
        ((MovieViewHolder)viewHolder).socialRank.setText(String.valueOf(Math.round(results.get(i).getPopularity())));
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
