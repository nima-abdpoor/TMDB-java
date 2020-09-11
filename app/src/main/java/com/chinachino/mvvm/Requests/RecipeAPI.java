package com.chinachino.mvvm.Requests;

import com.chinachino.mvvm.Requests.Responses.RecipeResponse;
import com.chinachino.mvvm.Requests.Responses.RecipeSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeAPI {
    @GET("api/search")
    Call<RecipeSearchResponse> searchResponses(
            @Query("q") String q,
            @Query("page") String page
    );

    @GET("api/get")
    Call<RecipeResponse> getRecipeResponses(
            @Query("rId") String id
    );
}
