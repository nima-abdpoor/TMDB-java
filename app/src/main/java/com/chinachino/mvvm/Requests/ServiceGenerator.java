package com.chinachino.mvvm.Requests;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.chinachino.mvvm.Utils.Constants.BASE_URL;

public class ServiceGenerator {
    private static Retrofit.Builder retrofitbuilder =
            new Retrofit.Builder().
                    addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL);

    private static Retrofit retrofit = retrofitbuilder.build();
    private static RecipeAPI recipeAPI = retrofit.create(RecipeAPI.class);

    public static RecipeAPI getRecipeAPI(){
        return recipeAPI;
    }
}