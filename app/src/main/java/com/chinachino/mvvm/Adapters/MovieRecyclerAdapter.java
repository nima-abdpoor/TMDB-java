package com.chinachino.mvvm.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.chinachino.mvvm.DrawGlide;
import com.chinachino.mvvm.R;
import com.chinachino.mvvm.models.Result;

import java.util.ArrayList;
import java.util.List;


import static com.chinachino.mvvm.Utils.Constants.DEFAULT_IMAGE_REQUEST;
import static com.chinachino.mvvm.Utils.Constants.IMAGE_BASE_URL;
import static com.chinachino.mvvm.Utils.Constants.DEFAULT_IMAGE;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "RecyclerView";
    private final int MOVIE_TYPE = 1;
    private final int LOADING_TYPE = 2;
    private final int ERROR_TYPE = 3;

    private boolean error;
     DrawGlide drawGlide;

    List<Result> OldList = new ArrayList<>();

    private OnMovieListener onMovieListener;
    private List<Result> results;


    public MovieRecyclerAdapter(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
        drawGlide = new DrawGlide();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d(TAG, "onCreateViewHolder: "+i);
        View view;
        switch (i) {
            case LOADING_TYPE: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_loading, viewGroup, false);
                return new LoadingViewHolder(view);
            }
            case MOVIE_TYPE: {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_list_item, viewGroup, false);
                return new MovieViewHolder(view, onMovieListener);
            }
            default:
            case ERROR_TYPE:{
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.error_in_list_movies,viewGroup,false);
                return new ErrorViewHolder(view);
            }
        }

    }
    public void ShowErrorResult(Context context){
        Toast.makeText(context,"check Internet Connection",Toast.LENGTH_SHORT).show();
        error = true;
        notifyDataSetChanged();
        Log.d(TAG, "ShowErrorResult: err");
    }

    public void setResults(List<Result> results) {
        if (results != null){
            error = false;
            this.results = results;
            //UpdateResulsts(results);
            notifyDataSetChanged();
        }
    }

    private void UpdateResulsts(List<Result> results) {
        MyDiffUtils diffUtils = new MyDiffUtils(OldList,results);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtils);
        results.clear();
        OldList.addAll(results);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: ");
        int itemViewType = getItemViewType(i);
        if (itemViewType == MOVIE_TYPE) {
            drawGlide.draw(
                    viewHolder.itemView.getContext(),
                    DEFAULT_IMAGE_REQUEST,
                    IMAGE_BASE_URL + results.get(i).getPosterPath(),
                    ((MovieViewHolder) viewHolder).image);

            ((MovieViewHolder) viewHolder).title.setText(results.get(i).getTitle());
            ((MovieViewHolder) viewHolder).description.setText(getOverview(results.get(i)));
            ((MovieViewHolder) viewHolder).releaseDate.setText(String.valueOf(results.get(i).getRelease_date()));
        }
        if (itemViewType == ERROR_TYPE){
            ((ErrorViewHolder) viewHolder).error.setText("Check Your Internet Connection!");
            ((ErrorViewHolder) viewHolder).button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
    public Integer getMovieID(int position){
        if (results !=null){
            if (results.size() >0 ){
                return results.get(position).getId();
            }
        }
        return null;
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
        if(error){
            return ERROR_TYPE;
        }
        else if (results.get(position).getTitle().equals("LOADING...")) {
            error = false;
            return LOADING_TYPE;
        }
        else if(position == results.size() -1 && position !=0){
            error = false;
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
