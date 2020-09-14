package com.chinachino.mvvm;

import android.util.Log;

import com.bumptech.glide.request.RequestOptions;
import com.chinachino.mvvm.models.Result;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class Filter {
    private static final String TAG = "Filter";

    public List<Result> filter(List<Result> results){
        for (Result result : results) {
            Log.d(TAG, "filter: " + result.getRelease_date());
        }
        List<Result> resultsList = (List<Result>) Observable.fromIterable(results)
                .map(Result::getRelease_date)
                .map(a -> a.replaceAll("-",""));
        for (Result result : resultsList) {
            Log.d(TAG, "filter: -->" + result.getRelease_date());
        }
        return resultsList;
    }
}
