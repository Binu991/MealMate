package com.example.mealmate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.adapters.MealAdapter;
import com.example.mealmate.helper.RecipeDao;
import com.example.mealmate.model.Dish;
import com.example.mealmate.model.Meal;

import java.util.ArrayList;
import java.util.List;

public class MealPlanScreenActivity extends AppCompatActivity {

    private ImageButton backButton;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private MealAdapter adapter;
    private ArrayList<Meal> mealList;
    private Button addRecipeButton;

    private RecyclerView yourRecipesRecyclerView;
    private MealAdapter yourRecipesAdapter;
    private ArrayList<Meal> yourRecipeList;

    private String source;
    private String dayName;
    private String date;

    private static final int REQUEST_COLLECT_RECIPE = 100;
    private static final int REQUEST_RECIPE_DETAIL = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan_screen);

        backButton = findViewById(R.id.backButton);
        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.mealRecyclerView);
        addRecipeButton = findViewById(R.id.tvAddRecipe);
        yourRecipesRecyclerView = findViewById(R.id.yourRecipesRecyclerView);

        source = getIntent().getStringExtra("source");
        dayName = getIntent().getStringExtra("dayName");
        date = getIntent().getStringExtra("date");

        mealList = new ArrayList<>();

        mealList.add(new Meal("Spaghetti Carbonara", Uri.parse("android.resource://com.example.mealmate/drawable/spha"),
                "200g Spaghetti\n2 Eggs\n100g Pancetta\n50g Parmesan cheese\nTo taste Black pepper",
                "1. Boil spaghetti.\n2. Cook pancetta.\n3. Mix eggs and cheese.\n4. Combine all and serve."));

        mealList.add(new Meal("Chicken Stir Fry", Uri.parse("android.resource://com.example.mealmate/drawable/chicken"),
                "250g Chicken\n1 cup Mixed vegetables\n2 tbsp Soy sauce\n2 cloves Garlic\n1 tsp Ginger",
                "1. Marinate chicken.\n2. Stir-fry chicken.\n3. Add vegetables and sauce.\n4. Serve hot."));

        mealList.add(new Meal("Paneer Butter Masala", Uri.parse("android.resource://com.example.mealmate/drawable/paneer"),
                "200g Paneer\n1 cup Tomato puree\n2 tbsp Butter\n3 tbsp Cream\n1 tsp Garam masala",
                "1. Fry paneer lightly.\n2. Cook tomato puree with spices.\n3. Add butter and cream.\n4. Mix paneer and simmer."));

        mealList.add(new Meal("Garlic Chicken", Uri.parse("android.resource://com.example.mealmate/drawable/garlic_chicken"),
                "300g Chicken\n6 cloves Garlic\n2 tbsp Soy sauce\n1 tbsp Oil\nSalt & Pepper to taste",
                "1. Marinate chicken with garlic.\n2. Pan-fry chicken.\n3. Add soy sauce.\n4. Cook thoroughly and serve."));

        mealList.add(new Meal("Veggie Pizza", Uri.parse("android.resource://com.example.mealmate/drawable/pizza"),
                "1 Pizza base\nTomato sauce\nCheese\nCapsicum\nOnion\nOlives",
                "1. Spread sauce\n2. Add toppings\n3. Sprinkle cheese\n4. Bake in oven until golden"));

        mealList.add(new Meal("Fruit Salad", Uri.parse("android.resource://com.example.mealmate/drawable/salad"),
                "1 Apple\n1 Banana\n5 Grapes\n1 Orange\n1 tbsp Honey",
                "1. Chop fruits\n2. Mix together\n3. Add honey\n4. Chill and serve"));

        mealList.add(new Meal("Grilled Sandwich", Uri.parse("android.resource://com.example.mealmate/drawable/sandwhich"),
                "2 slices Bread\nButter\nCheese\nTomato\nCucumber\nSalt & Pepper",
                "1. Layer ingredients\n2. Grill sandwich\n3. Cut and serve with ketchup"));

        mealList.add(new Meal("Egg Fried Rice", Uri.parse("android.resource://com.example.mealmate/drawable/rice"),
                "2 Eggs\n1 cup Rice\n1/2 cup Veggies\n1 tbsp Soy sauce\nOil",
                "1. Scramble eggs\n2. Add cooked rice\n3. Add veggies and soy sauce\n4. Stir-fry and serve"));

        mealList.add(new Meal("Tomato Soup", Uri.parse("android.resource://com.example.mealmate/drawable/soup"),
                "4 Tomatoes\n1 Onion\n1 tsp Butter\nSalt\nPepper",
                "1. Boil tomatoes\n2. Blend and strain\n3. Cook with onion and butter\n4. Season and serve hot"));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MealAdapter(mealList, meal -> {
            Intent intent = new Intent(MealPlanScreenActivity.this, RecipeDetailActivity.class);
            intent.putExtra("meal_name", meal.getName());
            intent.putExtra("meal_ingredients", meal.getIngredients());
            intent.putExtra("meal_instructions", meal.getInstructions());
            if (meal.isFromUri() && meal.getImageUri() != null) {
                intent.putExtra("image_uri", meal.getImageUri().toString());
            }
            if ("weekly".equals(source)) {
                intent.putExtra("source", source);
                intent.putExtra("dayName", dayName);
                intent.putExtra("date", date);
            }
            startActivityForResult(intent, REQUEST_RECIPE_DETAIL);
        });
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

        backButton.setOnClickListener(v -> finish());

        addRecipeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MealPlanScreenActivity.this, CollectRecipesActivity.class);
            startActivityForResult(intent, REQUEST_COLLECT_RECIPE);
        });

        RecipeDao dao = new RecipeDao(this);
        yourRecipeList = new ArrayList<>();
        List<Dish> savedDishes = dao.getAllDishes();
        for (Dish dish : savedDishes) {
            yourRecipeList.add(new Meal(
                    dish.getTitle(),
                    dish.getImageUri(),
                    dish.getIngredientsAsString(),
                    dish.getInstructionsAsString()
            ));
        }

        yourRecipesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        yourRecipesAdapter = new MealAdapter(yourRecipeList, meal -> {
            Intent intent = new Intent(MealPlanScreenActivity.this, RecipeDetailActivity.class);
            intent.putExtra("meal_name", meal.getName());
            intent.putExtra("meal_ingredients", meal.getIngredients());
            intent.putExtra("meal_instructions", meal.getInstructions());
            if (meal.isFromUri() && meal.getImageUri() != null) {
                intent.putExtra("image_uri", meal.getImageUri().toString());
            }
            if ("weekly".equals(source)) {
                intent.putExtra("source", source);
                intent.putExtra("dayName", dayName);
                intent.putExtra("date", date);
            }
            startActivityForResult(intent, REQUEST_RECIPE_DETAIL);
        });
        yourRecipesRecyclerView.setAdapter(yourRecipesAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_RECIPE_DETAIL && resultCode == RESULT_OK && data != null) {
            if ("weekly".equals(source)) {
                data.putExtra("source", source);
                data.putExtra("dayName", dayName);
                data.putExtra("date", date);
            }
            setResult(RESULT_OK, data);
            finish();
        }
        if (requestCode == REQUEST_COLLECT_RECIPE && resultCode == RESULT_OK && data != null) {
            Dish newDish = (Dish) data.getSerializableExtra("dish");
            if (newDish != null) {
                yourRecipeList.add(new Meal(
                        newDish.getTitle(),
                        newDish.getImageUri(),
                        newDish.getIngredientsAsString(),
                        newDish.getInstructionsAsString()
                ));
                yourRecipesAdapter.notifyItemInserted(yourRecipeList.size() - 1);
                Toast.makeText(this, "Dish added to Your Recipes!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
