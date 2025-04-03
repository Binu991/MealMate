package com.example.mealmate.model;

import java.util.List;

public class Recipe {
    private String title;
    private List<String> ingredients;
    private List<String> instructions;

    public Recipe(String title, List<String> ingredients, List<String> instructions) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getInstructions() {
        return instructions;
    }
}
