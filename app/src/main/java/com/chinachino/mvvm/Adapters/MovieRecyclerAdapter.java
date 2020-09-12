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

import static com.chinachino.mvvm.Utils.Constants.API_KEY;
import static com.chinachino.mvvm.Utils.Constants.BASE_URL;
import static com.chinachino.mvvm.Utils.Constants.IMAGE_BASE_URL;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnMovieListener onMovieListener;
    private List<Result> results;

    public MovieRecyclerAdapter(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
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
                .load(IMAGE_BASE_URL+results.get(i).getPosterPath())
                .into(((MovieViewHolder)viewHolder).image);

        ((MovieViewHolder)viewHolder).title.setText(results.get(i).getTitle());
        ((MovieViewHolder)viewHolder).description.setText(results.get(i).getOriginalLanguage());
        ((MovieViewHolder)viewHolder).socialRank.setText(String.valueOf(Math.round(results.get(i).getPopularity())));
    }


    @Override
    public int getItemCount() {
        if (results != null) {
            return results.size();
        }
        return 0;
    }
}
