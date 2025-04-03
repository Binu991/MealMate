package com.example.mealmate.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSessionManager {
    private static final String PREF_NAME = "MealMatePrefs";
    private static final String KEY_EMAIL = "user_email";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public UserSessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveUserEmail(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public String getUserEmail() {
        return prefs.getString(KEY_EMAIL, null);
    }

    // Clear user session
    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}
