package com.chinachino.mvvm;

import android.os.Bundle;
import android.util.Log;

import com.chinachino.mvvm.Requests.RecipeAPI;
import com.chinachino.mvvm.Requests.Responses.RecipeResponse;
import com.chinachino.mvvm.Requests.ServiceGenerator;
import com.chinachino.mvvm.models.Recipe;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity {
    private static final String TAG = "RecipeListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_recipe_list);
        TestSearRetrofit();
    }

    private void TestSearRetrofit() {
        RecipeAPI recipeAPI = ServiceGenerator.getRecipeAPI();
        recipeAPI.getRecipeResponses("41470")
                .enqueue(new Callback<RecipeResponse>() {
                    @Override
                    public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                        if (response.isSuccessful()) {
                            Log.d(TAG, "onResponse: code = " + response.code());
                            Log.d(TAG, "onResponse: response" + response.toString());
                            Log.d(TAG, "onResponse: " + response.body().toString());
                            Recipe recipe = response.body().getRecipe();

                            Log.d(TAG, "onResponse: title -> " + recipe.getTitle());

                        } else {
                            try {
                                Log.d(TAG, "onResponse: error" + response.errorBody().toString());
                            } catch (Exception e) {
                                Log.e(TAG, "onResponse: error", e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RecipeResponse> call, Throwable t) {

                    }
                });
    }
}