package com.example.mealmate.model;

import android.net.Uri;
import java.io.Serializable;

public class Meal implements Serializable {

    private String name;
    private int imageResId = -1;
    private Uri imageUri;
    private String ingredients;
    private String instructions;
    private long dateAdded;
    private String day;
    private String date;

    public Meal(String name, int imageResId) {
        this.name = name;
        this.imageResId = imageResId;
        this.ingredients = "Default ingredients for " + name;
        this.instructions = "Default instructions for " + name;
        this.dateAdded = System.currentTimeMillis();
    }


    public Meal(String name, int imageResId, String ingredients, String instructions) {
        this.name = name;
        this.imageResId = imageResId;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.dateAdded = System.currentTimeMillis();
    }


    public Meal(String name, Uri imageUri) {
        this.name = name;
        this.imageUri = imageUri;
        this.ingredients = "Default ingredients for " + name;
        this.instructions = "Default instructions for " + name;
        this.dateAdded = System.currentTimeMillis();
    }

    public Meal(String name, Uri imageUri, String ingredients, String instructions) {
        this.name = name;
        this.imageUri = imageUri;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.dateAdded = System.currentTimeMillis();
    }

    public String getName() {
        return name;
    }

    public int getImageResId() {
        return imageResId;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public boolean isFromUri() {
        return imageUri != null;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public String getDay() {
        return day;
    }

    public String getDate() {
        return date;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }
}
