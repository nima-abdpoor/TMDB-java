package com.chinachino.mvvm.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class Recipe implements Parcelable {
    private String[] ingredients;
    private String image_url;
    private String publisher;
    private String recipe_id;
    private String title;
    private float social_rank;

    public Recipe() {
    }

    public Recipe(String[] ingredients, String image_url,
                  String publisher, String recipe_id,
                  String title, float social_rank) {
        this.ingredients = ingredients;
        this.image_url = image_url;
        this.publisher = publisher;
        this.recipe_id = recipe_id;
        this.title = title;
        this.social_rank = social_rank;
    }

    protected Recipe(Parcel in) {
        ingredients = in.createStringArray();
        image_url = in.readString();
        publisher = in.readString();
        recipe_id = in.readString();
        title = in.readString();
        social_rank = in.readFloat();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String[] getIngredients() {
        return ingredients;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getSocial_rank() {
        return social_rank;
    }

    public void setSocial_rank(float social_rank) {
        this.social_rank = social_rank;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "ingredients=" + Arrays.toString(ingredients) +
                ", image_url='" + image_url + '\'' +
                ", publisher='" + publisher + '\'' +
                ", recipe_id='" + recipe_id + '\'' +
                ", title='" + title + '\'' +
                ", social_rank=" + social_rank +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(ingredients);
        dest.writeString(image_url);
        dest.writeString(publisher);
        dest.writeString(recipe_id);
        dest.writeString(title);
        dest.writeFloat(social_rank);
    }
}
