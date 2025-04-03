package com.example.mealmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mealmate.helper.UserSessionManager;

import java.util.ArrayList;
import java.util.List;

public class GroceryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "grocery_db";
    private static final int DB_VERSION = 5;

    public static final String TABLE_NAME = "grocery_items";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_STORE_NAME = "store_name";
    public static final String COLUMN_PURCHASED = "purchased";
    public static final String COLUMN_USER_EMAIL = "user_email";

    private final Context context;

    public GroceryDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME + " TEXT, " +
                COLUMN_QUANTITY + " TEXT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_PRICE + " TEXT, " +
                COLUMN_STORE_NAME + " TEXT, " +
                COLUMN_PURCHASED + " INTEGER DEFAULT 0, " +
                COLUMN_USER_EMAIL + " TEXT, " +
                "PRIMARY KEY (" + COLUMN_NAME + ", " + COLUMN_USER_EMAIL + "))";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertOrUpdateItem(GroceryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        String userEmail = new UserSessionManager(context).getUserEmail();

        Cursor cursor = db.query(TABLE_NAME, null,
                COLUMN_NAME + "=? AND " + COLUMN_USER_EMAIL + "=?",
                new String[]{item.getName(), userEmail}, null, null, null);

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, item.getName());
        values.put(COLUMN_QUANTITY, item.getQuantity());
        values.put(COLUMN_CATEGORY, item.getCategory());
        values.put(COLUMN_PRICE, item.getPrice());
        values.put(COLUMN_STORE_NAME, item.getStoreName());
        values.put(COLUMN_PURCHASED, item.isPurchased() ? 1 : 0);
        values.put(COLUMN_USER_EMAIL, userEmail);

        if (cursor.moveToFirst()) {
            db.update(TABLE_NAME, values,
                    COLUMN_NAME + "=? AND " + COLUMN_USER_EMAIL + "=?",
                    new String[]{item.getName(), userEmail});
        } else {
            db.insert(TABLE_NAME, null, values);
        }

        cursor.close();
        db.close();
    }

    public List<GroceryItem> getAllItems() {
        List<GroceryItem> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String userEmail = new UserSessionManager(context).getUserEmail();

        Cursor cursor = db.query(TABLE_NAME, null,
                COLUMN_USER_EMAIL + "=?", new String[]{userEmail},
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                items.add(extractItemFromCursor(cursor));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return items;
    }

    public List<GroceryItem> getPurchasedItems() {
        List<GroceryItem> items = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String userEmail = new UserSessionManager(context).getUserEmail();

        Cursor cursor = db.query(TABLE_NAME, null,
                COLUMN_PURCHASED + "=? AND " + COLUMN_USER_EMAIL + "=?",
                new String[]{"1", userEmail},
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                items.add(extractItemFromCursor(cursor));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return items;
    }

    public void deleteItem(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String userEmail = new UserSessionManager(context).getUserEmail();

        db.delete(TABLE_NAME,
                COLUMN_NAME + "=? AND " + COLUMN_USER_EMAIL + "=?",
                new String[]{name, userEmail});
        db.close();
    }

    public void clearAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        String userEmail = new UserSessionManager(context).getUserEmail();
        db.delete(TABLE_NAME, COLUMN_USER_EMAIL + "=?", new String[]{userEmail});
        db.close();
    }

    private GroceryItem extractItemFromCursor(Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
        String quantity = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));
        String category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY));
        String price = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
        String storeName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STORE_NAME));
        int purchasedInt = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PURCHASED));
        boolean purchased = purchasedInt == 1;
        return new GroceryItem(name, quantity, category, price, storeName, purchased);
    }
}
