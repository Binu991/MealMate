package com.example.mealmate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.adapters.MealAdapter;
import com.example.mealmate.helper.DummyMealsProvider;
import com.example.mealmate.helper.RecipeDao;
import com.example.mealmate.model.Dish;
import com.example.mealmate.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private MealAdapter adapter;
    private ArrayList<Meal> mealList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        DummyMealsProvider.insertDummyMealsIfNeeded(this);

        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerViewSearch);

        mealList = new ArrayList<>();

        RecipeDao dao = new RecipeDao(this);
        List<Dish> dishes = dao.getAllDishes();
        for (Dish dish : dishes) {
            Log.d("SearchActivity", "Loaded: " + dish.getTitle());
            mealList.add(new Meal(
                    dish.getTitle(),
                    dish.getImageUri(),
                    dish.getIngredientsAsString(),
                    dish.getInstructionsAsString()
            ));
        }

        adapter = new MealAdapter(mealList, meal -> {
            Intent intent = new Intent(SearchActivity.this, RecipeDetailActivity.class);
            intent.putExtra("meal_name", meal.getName());
            intent.putExtra("meal_ingredients", meal.getIngredients());
            intent.putExtra("meal_instructions", meal.getInstructions());

            if (meal.isFromUri() && meal.getImageUri() != null) {
                intent.putExtra("image_uri", meal.getImageUri().toString());
            } else {
                intent.putExtra("image_res_id", meal.getImageResId());
            }

            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
    }
}
