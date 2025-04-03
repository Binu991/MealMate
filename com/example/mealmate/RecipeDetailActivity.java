package com.example.mealmate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class RecipeDetailActivity extends AppCompatActivity {

    private static final String TAG = "RecipeDetailActivity";

    private ImageView mealImage;
    private TextView mealName, mealIngredients, mealInstructions;
    private Button addToMealPlanButton;

    private String source = "main";
    private String dayName, date;

    private String ingredients;
    private String instructions;
    private String imageUriString;
    private int imageResId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Initialize Views
        mealImage = findViewById(R.id.mealImage);
        mealName = findViewById(R.id.mealName);
        mealIngredients = findViewById(R.id.mealIngredients);
        mealInstructions = findViewById(R.id.mealInstructions);
        addToMealPlanButton = findViewById(R.id.addToMealPlanButton);

        // Get Intent Data
        Intent intent = getIntent();
        String name = intent.getStringExtra("meal_name");
        ingredients = intent.getStringExtra("meal_ingredients");
        instructions = intent.getStringExtra("meal_instructions");
        imageUriString = intent.getStringExtra("image_uri");
        imageResId = intent.getIntExtra("image_res_id", -1);
        source = intent.getStringExtra("source") != null ? intent.getStringExtra("source") : "main";
        dayName = intent.getStringExtra("dayName");
        date = intent.getStringExtra("date");

        if (name != null) mealName.setText(name);

        if (ingredients != null && !ingredients.isEmpty()) {
            StringBuilder formattedIngredients = new StringBuilder();
            for (String ing : ingredients.split("[,\n]")) {
                formattedIngredients.append("• ").append(ing.trim()).append("\n");
            }
            SpannableString text = new SpannableString("🧾 Ingredients:\n" + formattedIngredients);
            text.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, "🧾 Ingredients:\n".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mealIngredients.setText(text);
        }

        if (instructions != null && !instructions.isEmpty()) {
            StringBuilder formattedInstructions = new StringBuilder();
            String[] steps = instructions.split("[\n]");
            for (int i = 0; i < steps.length; i++) {
                formattedInstructions.append((i + 1)).append(". ").append(steps[i].trim()).append("\n");
            }
            SpannableString text = new SpannableString("👨‍🍳 Instructions:\n" + formattedInstructions);
            text.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, "👨‍🍳 Instructions:\n".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mealInstructions.setText(text);
        }

        if (imageUriString != null && !imageUriString.isEmpty()) {
            try {
                Uri imageUri = Uri.parse(imageUriString);
                Glide.with(this)
                        .load(imageUri)
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.placeholder_image)
                        .into(mealImage);
            } catch (Exception e) {
                Log.e(TAG, "Image load failed, fallback to placeholder", e);
                mealImage.setImageResource(R.drawable.placeholder_image);
            }
        } else if (imageResId != -1) {
            Glide.with(this)
                    .load(imageResId)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(mealImage);
        } else {
            mealImage.setImageResource(R.drawable.placeholder_image);
        }

        addToMealPlanButton.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("meal_name", mealName.getText().toString());
            resultIntent.putExtra("meal_ingredients", ingredients);
            resultIntent.putExtra("meal_instructions", instructions);

            if (imageUriString != null && !imageUriString.isEmpty()) {
                resultIntent.putExtra("image_uri", imageUriString);
            } else if (imageResId != -1) {
                resultIntent.putExtra("image_res_id", imageResId);
            }

            if ("weekly".equals(source)) {
                resultIntent.putExtra("source", "weekly");
                resultIntent.putExtra("dayName", dayName);
                resultIntent.putExtra("date", date);
                Toast.makeText(this, "Meal added to weekly plan!", Toast.LENGTH_SHORT).show();
            } else {
                GroceryDatabaseHelper dbHelper = new GroceryDatabaseHelper(this);
                if (ingredients != null && !ingredients.isEmpty()) {
                    for (String item : ingredients.split("[,\n]")) {
                        String trimmed = item.trim();
                        if (!trimmed.isEmpty()) {
                            String category = getCategoryForIngredient(trimmed);
                            GroceryItem groceryItem = new GroceryItem(trimmed, "1", category);
                            dbHelper.insertOrUpdateItem(groceryItem);
                        }
                    }
                }
                Toast.makeText(this, "Meal added with categorized grocery items!", Toast.LENGTH_SHORT).show();
            }

            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private String getCategoryForIngredient(String name) {
        name = name.toLowerCase();
        if (name.contains("tomato") || name.contains("onion") || name.contains("carrot") || name.contains("potato") || name.contains("broccoli")) {
            return "Vegetables";
        } else if (name.contains("chicken") || name.contains("egg") || name.contains("beef") || name.contains("fish") || name.contains("meat")) {
            return "Proteins";
        } else if (name.contains("milk") || name.contains("cheese") || name.contains("yogurt") || name.contains("butter")) {
            return "Dairy";
        } else if (name.contains("bread") || name.contains("rice") || name.contains("pasta") || name.contains("flour") || name.contains("noodle")) {
            return "Grains";
        } else if (name.contains("apple") || name.contains("banana") || name.contains("mango") || name.contains("fruit")) {
            return "Fruits";
        } else {
            return "Uncategorized";
        }
    }
}
