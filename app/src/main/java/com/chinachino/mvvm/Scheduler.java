package com.chinachino.mvvm;

import androidx.lifecycle.MutableLiveData;

import com.chinachino.mvvm.Executors.AppExecutors;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.chinachino.mvvm.Utils.Constants.TIME_OUT;

public class Scheduler {
    public void Schedule(Future future, MutableLiveData<Boolean> RequestTimeOut){
        AppExecutors.getInstance().getExecutorService().schedule(() -> {
            RequestTimeOut.postValue(true);
            future.cancel(true);
        }, TIME_OUT, TimeUnit.SECONDS);
    }
}
