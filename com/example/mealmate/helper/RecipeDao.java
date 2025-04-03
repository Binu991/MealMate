package com.example.mealmate.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mealmate.model.Dish;

import java.util.ArrayList;
import java.util.List;

public class RecipeDao {

    private final RecipeDatabaseHelper dbHelper;

    public RecipeDao(Context context) {
        dbHelper = new RecipeDatabaseHelper(context);
    }

    public void insertDish(Dish dish) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(RecipeDatabaseHelper.COLUMN_TITLE, dish.getTitle());
        values.put(RecipeDatabaseHelper.COLUMN_INGREDIENTS, dish.getIngredientsAsString());
        values.put(RecipeDatabaseHelper.COLUMN_INSTRUCTIONS, dish.getInstructionsAsString());
        values.put(RecipeDatabaseHelper.COLUMN_IMAGE_URI, dish.getImageUri().toString());

        db.insert(RecipeDatabaseHelper.TABLE_NAME, null, values);
        db.close();
    }

    public List<Dish> getAllDishes() {
        List<Dish> dishes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                RecipeDatabaseHelper.TABLE_NAME,
                null, null, null, null, null, null
        );

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(RecipeDatabaseHelper.COLUMN_TITLE));
                String ingredientsStr = cursor.getString(cursor.getColumnIndexOrThrow(RecipeDatabaseHelper.COLUMN_INGREDIENTS));
                String instructionsStr = cursor.getString(cursor.getColumnIndexOrThrow(RecipeDatabaseHelper.COLUMN_INSTRUCTIONS));
                String imageUriStr = cursor.getString(cursor.getColumnIndexOrThrow(RecipeDatabaseHelper.COLUMN_IMAGE_URI));

                Dish dish = new Dish(
                        title,
                        Dish.convertStringToList(ingredientsStr),
                        Dish.convertStringToList(instructionsStr),
                        android.net.Uri.parse(imageUriStr)
                );

                dishes.add(dish);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return dishes;
    }
}
