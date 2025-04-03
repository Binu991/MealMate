package com.example.mealmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.net.Uri;
import java.util.ArrayList;

public class RecipeDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "meal_recipes.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "recipes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_IMAGE_URI = "image_uri";
    private static final String COLUMN_INGREDIENTS = "ingredients";
    private static final String COLUMN_INSTRUCTIONS = "instructions";

    public RecipeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_IMAGE_URI + " TEXT," +
                COLUMN_INGREDIENTS + " TEXT," +
                COLUMN_INSTRUCTIONS + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertRecipe(String title, String imageUri, String ingredients, String instructions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_IMAGE_URI, imageUri);
        values.put(COLUMN_INGREDIENTS, ingredients);
        values.put(COLUMN_INSTRUCTIONS, instructions);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<RecipeStorage.CustomRecipe> getAllRecipes() {
        ArrayList<RecipeStorage.CustomRecipe> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_ID + " DESC");

        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                String imageUri = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URI));
                String ingredients = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENTS));
                String instructions = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INSTRUCTIONS));

                Uri uri = imageUri != null ? Uri.parse(imageUri) : null;
                list.add(new RecipeStorage.CustomRecipe(title, uri, ingredients, instructions));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
