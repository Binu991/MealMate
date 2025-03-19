package com.example.mealmate.ingredients;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredient_table")
public class Ingredient {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String description;
    private int quantity;
    private double price;
    private String imageUri;
    private boolean purchased;
}
