package com.chinachino.mvvm.Utils;

import android.util.Log;

import com.chinachino.mvvm.models.Result;

import java.util.List;

public class Testing {
    public static void Test(List<Result> results,String tag){
        for (Result result : results)
            Log.d(tag, tag +" : " +result.getTitle());
    }
}
