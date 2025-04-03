package com.example.mealmate;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mealmate.helper.NotificationHelper;
import com.example.mealmate.helper.NotificationReceiver;

import java.util.Calendar;

public class NotificationSettingsActivity extends AppCompatActivity {

    private Button btnMealPrep, btnGroceryReminder, btnDailyPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);

        NotificationHelper.createNotificationChannel(this);

        btnMealPrep = findViewById(R.id.btnMealPrepReminder);
        btnGroceryReminder = findViewById(R.id.btnGroceryReminder);
        btnDailyPlan = findViewById(R.id.btnDailyMealPlanReminder);

        btnMealPrep.setOnClickListener(v -> scheduleNotification("Meal Prep Reminder", "Time to prepare your meal!", 9));
        btnGroceryReminder.setOnClickListener(v -> scheduleNotification("Grocery Reminder", "Don't forget your groceries!", 17));
        btnDailyPlan.setOnClickListener(v -> scheduleNotification("Daily Meal Plan", "Here's your meal plan for the day!", 7));
    }

    private void scheduleNotification(String title, String message, int hourOfDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        // If the time is in the past, schedule for next day
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("title", title);
        intent.putExtra("message", message);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                (int) System.currentTimeMillis(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                pendingIntent
        );

        Toast.makeText(this, title + " set at " + hourOfDay + ":00", Toast.LENGTH_SHORT).show();
    }
}
