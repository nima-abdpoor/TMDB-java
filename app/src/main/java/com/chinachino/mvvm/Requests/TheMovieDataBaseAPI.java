package com.chinachino.mvvm.Requests;

import com.chinachino.mvvm.models.Details;
import com.chinachino.mvvm.models.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieDataBaseAPI {
    @GET("search/movie")
    Call<Example> SearchResponse(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("query") String query,
            @Query("page") int page,
            @Query("include_adult") boolean include_adult
    );
    @GET("movie/{movieID}")
    Call<Details> getMovieDetails(
            @Path("movieID") int movieID,
            @Query("api_key") String key,
            @Query("language") String language
    );
}
