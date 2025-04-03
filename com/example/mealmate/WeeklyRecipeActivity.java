package com.example.mealmate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.adapters.WeeklyRecipeAdapter;
import com.example.mealmate.helper.WeeklyPlanDatabaseHelper;
import com.example.mealmate.model.Meal;
import com.example.mealmate.model.WeeklyDay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class WeeklyRecipeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private WeeklyRecipeAdapter adapter;
    private ArrayList<WeeklyDay> weeklyList;
    private Button btnSavePlan;

    private static final int REQUEST_ADD_MEAL_WEEKLY = 3000;

    private final HashMap<String, ArrayList<Meal>> mealsPerDay = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_recipe);

        recyclerView = findViewById(R.id.recyclerViewWeekly);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnSavePlan = findViewById(R.id.btnSavePlan);

        weeklyList = generateWeeklyPlan();

        adapter = new WeeklyRecipeAdapter(weeklyList, day -> {
            Intent intent = new Intent(WeeklyRecipeActivity.this, MealPlanScreenActivity.class);
            intent.putExtra("source", "weekly");
            intent.putExtra("dayName", day.getDayName());
            intent.putExtra("date", day.getDate());
            startActivityForResult(intent, REQUEST_ADD_MEAL_WEEKLY);
        });

        recyclerView.setAdapter(adapter);

        btnSavePlan.setOnClickListener(v -> saveWeeklyPlan());
    }

    private ArrayList<WeeklyDay> generateWeeklyPlan() {
        ArrayList<WeeklyDay> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1); // Start from tomorrow

        for (int i = 0; i < 7; i++) {
            String dayName = new SimpleDateFormat("EEEE", Locale.getDefault()).format(calendar.getTime());
            String date = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(calendar.getTime());
            WeeklyDay day = new WeeklyDay(dayName, date);
            day.setMeals(mealsPerDay.getOrDefault(dayName, new ArrayList<>()));
            list.add(day);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return list;
    }

    private void saveWeeklyPlan() {
        WeeklyPlanDatabaseHelper dbHelper = new WeeklyPlanDatabaseHelper(this);
        dbHelper.clearWeeklyPlan();

        for (WeeklyDay day : weeklyList) {
            for (Meal meal : day.getMeals()) {
                String imageUriStr = meal.getImageUri() != null ? meal.getImageUri().toString() : null;
                dbHelper.insertMeal(
                        day.getDayName(),
                        day.getDate(),
                        meal.getName(),
                        imageUriStr,
                        meal.getIngredients(),
                        meal.getInstructions()
                );
            }
        }

        Toast.makeText(this, "Weekly meal plan saved successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADD_MEAL_WEEKLY && resultCode == RESULT_OK && data != null) {
            String mealName = data.getStringExtra("meal_name");
            String ingredients = data.getStringExtra("meal_ingredients");
            String instructions = data.getStringExtra("meal_instructions");
            String imageUriString = data.getStringExtra("image_uri");
            String dayName = data.getStringExtra("dayName");

            if (mealName != null && dayName != null) {
                Meal newMeal = new Meal(mealName,
                        imageUriString != null ? Uri.parse(imageUriString) : null,
                        ingredients,
                        instructions);

                mealsPerDay.putIfAbsent(dayName, new ArrayList<>());
                mealsPerDay.get(dayName).add(newMeal);

                for (WeeklyDay day : weeklyList) {
                    if (day.getDayName().equals(dayName)) {
                        day.setMeals(mealsPerDay.get(dayName));
                        break;
                    }
                }

                adapter.notifyDataSetChanged();

                Toast.makeText(this, "Meal added to " + dayName + "'s plan!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
