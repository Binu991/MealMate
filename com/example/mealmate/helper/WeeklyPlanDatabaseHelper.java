package com.example.mealmate.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.example.mealmate.model.Meal;

import java.util.ArrayList;
import java.util.HashMap;

public class WeeklyPlanDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "weekly_meal_plan.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "weekly_meal_plan";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_MEAL_NAME = "meal_name";
    public static final String COLUMN_IMAGE_URI = "image_uri";
    public static final String COLUMN_INGREDIENTS = "ingredients";
    public static final String COLUMN_INSTRUCTIONS = "instructions";

    public WeeklyPlanDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DAY + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_MEAL_NAME + " TEXT, " +
                COLUMN_IMAGE_URI + " TEXT, " +
                COLUMN_INGREDIENTS + " TEXT, " +
                COLUMN_INSTRUCTIONS + " TEXT" +
                ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertMeal(String day, String date, String name, String imageUri, String ingredients, String instructions) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_DAY, day);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_MEAL_NAME, name);
        values.put(COLUMN_IMAGE_URI, imageUri);
        values.put(COLUMN_INGREDIENTS, ingredients);
        values.put(COLUMN_INSTRUCTIONS, instructions);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void clearWeeklyPlan() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    public Cursor getAllMeals() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_DAY + ", " + COLUMN_ID, null);
    }

    public HashMap<String, ArrayList<Meal>> getGroupedMealsByDay() {
        HashMap<String, ArrayList<Meal>> map = new HashMap<>();
        Cursor cursor = getAllMeals();

        if (cursor.moveToFirst()) {
            do {
                String day = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAY));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEAL_NAME));
                String imageUriString = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_URI));
                String ingredients = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENTS));
                String instructions = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INSTRUCTIONS));

                Uri imageUri = imageUriString != null ? Uri.parse(imageUriString) : null;

                Meal meal = new Meal(name, imageUri, ingredients, instructions);
                meal.setDay(day);
                meal.setDate(date);

                String key = day + " - " + date;
                if (!map.containsKey(key)) {
                    map.put(key, new ArrayList<>());
                }
                map.get(key).add(meal);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return map;
    }
}
