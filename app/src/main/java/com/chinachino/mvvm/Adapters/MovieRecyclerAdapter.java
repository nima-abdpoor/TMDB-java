package com.chinachino.mvvm.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chinachino.mvvm.R;
import com.chinachino.mvvm.models.Result;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.chinachino.mvvm.Utils.Constants.IMAGE_BASE_URL;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int MOVIE_TYPE = 1;
    private final int LOADING_TYPE = 2;

    private OnMovieListener onMovieListener;
    private List<Result> results;

    public MovieRecyclerAdapter(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        switch (i) {
            case LOADING_TYPE: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_loading, viewGroup, false);
                return new LoadingViewHolder(view);
            }
            case MOVIE_TYPE:
            default: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_list_item, viewGroup, false);
                return new MovieViewHolder(view, onMovieListener);
            }
        }

    }

    public void setResults(List<Result> results) {
        this.results = results;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int itemViewType = getItemViewType(i);
        if (itemViewType == MOVIE_TYPE) {
            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
            Glide.with(viewHolder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(IMAGE_BASE_URL + results.get(i).getPosterPath())
                    .into(((MovieViewHolder) viewHolder).image);

            ((MovieViewHolder) viewHolder).title.setText(results.get(i).getTitle());
            ((MovieViewHolder) viewHolder).description.setText(getOverview(results.get(i)));
            ((MovieViewHolder) viewHolder).releaseDate.setText(String.valueOf(results.get(i).getRelease_date()));
        }
    }

    private String getOverview(Result result) {
        try {
            return result.getOverview().substring(0,20)+"...";
        }
        catch (StringIndexOutOfBoundsException s){
            Log.d(TAG, "getOverview: "+s);
        }
        return "...";
    }

    @Override
    public int getItemViewType(int position) {
        if (results.get(position).getTitle().equals("LOADING...")) {
            return LOADING_TYPE;
        }
        else if(position == results.size() -1 && position !=0){
            return LOADING_TYPE;
        }
    else return MOVIE_TYPE;
    }

    public void displayLoading() {
        if (!isLoading()) {
            Result result = new Result();
            result.setTitle("LOADING...");
            List<Result> loadingResults = new ArrayList<>();
            loadingResults.add(result);
            results = loadingResults;
            notifyDataSetChanged();
        }
    }

    private boolean isLoading() {
        if (results !=null){
            if (results.size() > 0) {
                if (results.get(results.size() - 1).getTitle().equals("LOADING ...")) {
                    return true;
                }
            }
        }
        return false;

    }

    @Override
    public int getItemCount() {
        if (results != null) {
            return results.size();
        }
        return 0;
    }
}
