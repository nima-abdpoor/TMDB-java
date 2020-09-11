package com.chinachino.mvvm;

import android.os.Bundle;
import android.util.Log;

import com.chinachino.mvvm.Requests.RecipeAPI;
import com.chinachino.mvvm.Requests.Responses.RecipeSearchResponse;
import com.chinachino.mvvm.Requests.ServiceGenerator;
import com.chinachino.mvvm.models.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity {
    private static final String TAG = "RecipeListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_recipe_list);
        TestRetrofit();
    }
    private void TestRetrofit(){
        RecipeAPI recipeAPI = ServiceGenerator.getRecipeAPI();
        recipeAPI.searchResponses("chicken","1")
                .enqueue(new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                if(response.isSuccessful()) {
                    Log.d(TAG, "onResponse: code = " + response.code());
                    Log.d(TAG, "onResponse: response" + response.toString());
                    Log.d(TAG, "onResponse: " + response.body().toString());
                    List<Recipe> recipeList = new ArrayList<>(response.body().getRecipes());
                    for (Recipe recipe : recipeList) {
                        Log.d(TAG, "onResponse: title -> "+recipe.getTitle());
                    }
                }
                else {
                    try {
                        Log.d(TAG, "onResponse: error"+response.errorBody().toString());
                    }
                    catch (Exception e) {
                        Log.e(TAG, "onResponse: error",e );
                    }
                }
            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {

            }
        });
    }
}