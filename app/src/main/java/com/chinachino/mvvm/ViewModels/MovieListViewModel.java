package com.chinachino.mvvm.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.chinachino.mvvm.models.Result;

import java.util.List;

public class MovieListViewModel extends ViewModel {
    private MutableLiveData<List<Result>> mutableLiveData=new MutableLiveData<>();
    public MovieListViewModel() {

    }

    public MutableLiveData<List<Result>> getMovies() {
        return mutableLiveData;
    }
}
