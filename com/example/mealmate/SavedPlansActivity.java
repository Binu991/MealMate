package com.example.mealmate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mealmate.helper.WeeklyPlanDatabaseHelper;
import com.example.mealmate.model.Meal;

import java.util.ArrayList;
import java.util.HashMap;

public class SavedPlansActivity extends AppCompatActivity {

    private LinearLayout linearLayoutSavedPlans;
    private WeeklyPlanDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_plans);

        linearLayoutSavedPlans = findViewById(R.id.linearLayoutSavedPlans);
        dbHelper = new WeeklyPlanDatabaseHelper(this);

        displaySavedMeals();
    }

    private void displaySavedMeals() {
        HashMap<String, ArrayList<Meal>> groupedMeals = dbHelper.getGroupedMealsByDay();

        for (String day : groupedMeals.keySet()) {
            TextView dayTitle = new TextView(this);
            dayTitle.setText(day);
            dayTitle.setTextSize(18);
            dayTitle.setTextColor(getResources().getColor(android.R.color.black));
            dayTitle.setPadding(0, 16, 0, 8);
            linearLayoutSavedPlans.addView(dayTitle);

            for (Meal meal : groupedMeals.get(day)) {
                View mealView = LayoutInflater.from(this).inflate(R.layout.item_meal_horizontal, linearLayoutSavedPlans, false);

                TextView title = mealView.findViewById(R.id.tvMealTitle);
                ImageView image = mealView.findViewById(R.id.ivMealImage);

                title.setText(meal.getName());

                if (meal.getImageUri() != null) {
                    Glide.with(this)
                            .load(meal.getImageUri())
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.placeholder_image)
                            .into(image);
                } else if (meal.getImageResId() != -1) {
                    Glide.with(this)
                            .load(meal.getImageResId())
                            .placeholder(R.drawable.placeholder_image)
                            .error(R.drawable.placeholder_image)
                            .into(image);
                } else {
                    image.setImageResource(R.drawable.placeholder_image);
                }

                linearLayoutSavedPlans.addView(mealView);
            }
        }
    }
}
