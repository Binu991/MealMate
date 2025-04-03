package com.example.mealmate;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.adapters.MealAdapter;
import com.example.mealmate.model.Meal;

import java.util.ArrayList;

public class YourRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MealAdapter adapter;
    private ArrayList<Meal> yourRecipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_recipes);

        recyclerView = findViewById(R.id.yourRecipesRecyclerView);
        yourRecipeList = new ArrayList<>();

        yourRecipeList.add(new Meal("My Custom Pasta", R.drawable.placeholder_image));
        yourRecipeList.add(new Meal("Tandoori Wrap", R.drawable.placeholder_image));

        adapter = new MealAdapter(yourRecipeList, meal -> {
            Toast.makeText(YourRecipesActivity.this,
                    "Clicked: " + meal.getName(),
                    Toast.LENGTH_SHORT).show();

        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
