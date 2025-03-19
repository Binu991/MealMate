package com.example.mealmate.ingredients;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IngredientDao {

    @Insert
    void insert(Ingredient ingredient);

    @Update
    void update(Ingredient ingredient);

    @Delete
    void delete(Ingredient ingredient);

    @Query("SELECT * FROM ingredient_table WHERE id = :id")
    Ingredient getById(int id);

    @Query("SELECT * FROM ingredient_table")
    List<Ingredient> getAllIngredients();
}
