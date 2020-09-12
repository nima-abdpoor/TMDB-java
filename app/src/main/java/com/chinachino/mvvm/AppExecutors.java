package com.chinachino.mvvm;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
    private static AppExecutors instance;
    public static AppExecutors getInstance(){
        if (instance == null){
            instance = new AppExecutors();
        }
        return instance;
    }
    private AppExecutors(){

    }
    public ScheduledExecutorService getExecutorService(){
        return executorService;
    }

}
