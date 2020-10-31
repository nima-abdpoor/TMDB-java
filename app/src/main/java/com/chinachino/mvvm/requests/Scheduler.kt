package com.chinachino.mvvm.requests

import androidx.lifecycle.MutableLiveData
import com.chinachino.mvvm.utils.Constants
import com.chinachino.mvvm.executors.AppExecutors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

class Scheduler {
    fun schedule(future: Future<*>, RequestTimeOut: MutableLiveData<Boolean?>) {
        AppExecutors.executorService.schedule({
            RequestTimeOut.postValue(true)
            future.cancel(true)
        }, Constants.TIME_OUT, TimeUnit.SECONDS)
    }
}