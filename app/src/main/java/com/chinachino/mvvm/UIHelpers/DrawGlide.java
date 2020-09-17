package com.chinachino.mvvm.UIHelpers;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DrawGlide {


    public DrawGlide() {

    }
    public  void draw(Context context, RequestOptions requestOptions, String loadAddress, ImageView into){
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(loadAddress)
                .into((into));
    }
}
