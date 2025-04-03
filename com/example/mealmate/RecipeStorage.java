package com.example.mealmate;

import android.net.Uri;
import java.util.ArrayList;

public class RecipeStorage {

    public static ArrayList<CustomRecipe> customRecipes = new ArrayList<>();

    public static void addRecipe(CustomRecipe recipe) {
        customRecipes.add(recipe);
    }

    public static ArrayList<CustomRecipe> getRecipes() {
        return customRecipes;
    }

    public static class CustomRecipe {
        public String title;
        public Uri imageUri;
        public String ingredients;
        public String instructions;

        public CustomRecipe(String title, Uri imageUri, String ingredients, String instructions) {
            this.title = title;
            this.imageUri = imageUri;
            this.ingredients = ingredients;
            this.instructions = instructions;
        }
    }
}
