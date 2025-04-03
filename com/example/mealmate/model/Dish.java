package com.example.mealmate.model;

import android.net.Uri;
import java.io.Serializable;
import java.util.List;

public class Dish implements Serializable {
    private String title;
    private List<String> ingredients;
    private List<String> instructions;
    private Uri imageUri;

    public Dish(String title, List<String> ingredients, List<String> instructions, Uri imageUri) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.imageUri = imageUri;
    }

    public String getTitle() { return title; }
    public List<String> getIngredients() { return ingredients; }
    public List<String> getInstructions() { return instructions; }
    public Uri getImageUri() { return imageUri; }

    public String getIngredientsAsString() {
        return String.join(",", ingredients);
    }

    public String getInstructionsAsString() {
        return String.join(",", instructions);
    }

    public static List<String> convertStringToList(String data) {
        return List.of(data.split(","));
    }
}
