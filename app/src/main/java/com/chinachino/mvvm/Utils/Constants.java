package com.chinachino.mvvm.Utils;

import com.bumptech.glide.request.RequestOptions;
import com.chinachino.mvvm.R;

public class Constants {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    public static final String API_KEY = "602e06820fbac2f033f50027e0fe5277";
    public static final int TIME_OUT = 6;
    public static final String DEFAULT_LANGUAGE = "en-US";
    public static final int DEFAULT_PAGE = 1;
    public static final boolean DEFAULT_ADULT = false;
    public static final String[] DEFAULT_MOVIE_LIST_NAME = {"life","toy"
            ,"war","nature","nina","big","peace","rain","murder"};
    public static RequestOptions DEFAULT_IMAGE_REQUEST = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
    public static String DEFAULT_IMAGE = String.valueOf(R.drawable.ic_launcher_background);
}
