package com.example.mealmate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mealmate.helper.WeeklyPlanDatabaseHelper;
import com.google.android.material.card.MaterialCardView;

public class SettingsActivity extends AppCompatActivity {

    private MaterialCardView cardProfile, cardPurchasedItems, cardSavedPlans, cardClearData, cardLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        cardProfile = findViewById(R.id.cardProfile);
        cardPurchasedItems = findViewById(R.id.cardPurchasedItems);
        cardSavedPlans = findViewById(R.id.cardSavedPlans);
        cardClearData = findViewById(R.id.cardClearData);
        cardLogout = findViewById(R.id.cardLogout);

        cardProfile.setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });

        cardPurchasedItems.setOnClickListener(v -> {
            startActivity(new Intent(this, PurchasedItemsActivity.class));
        });

        cardSavedPlans.setOnClickListener(v -> {
            startActivity(new Intent(this, SavedPlansActivity.class));
        });

        cardClearData.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Clear All Data")
                    .setMessage("Are you sure you want to delete all grocery and meal plan data?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        GroceryDatabaseHelper groceryDb = new GroceryDatabaseHelper(this);
                        WeeklyPlanDatabaseHelper weeklyDb = new WeeklyPlanDatabaseHelper(this);
                        groceryDb.clearAll();
                        weeklyDb.clearWeeklyPlan();
                        Toast.makeText(this, "All data cleared!", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        cardLogout.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Logout")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        // You can add FirebaseAuth.getInstance().signOut(); here if needed
                        Intent intent = new Intent(this, LogIn.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }
}
