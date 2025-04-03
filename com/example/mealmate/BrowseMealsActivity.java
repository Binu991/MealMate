package com.example.mealmate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mealmate.model.Recipe;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class BrowseMealsActivity extends AppCompatActivity {

    private EditText urlEditText;
    private Button importButton;
    private TextView instructionsTextView;
    private Button btnBack;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browsemeals);

        urlEditText = findViewById(R.id.urlEditText);
        importButton = findViewById(R.id.importButton);
        instructionsTextView = findViewById(R.id.instructionsTextView);
        btnBack = findViewById(R.id.btnBack);
        progressBar = findViewById(R.id.progressBar);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = urlEditText.getText().toString().trim();
                if (!url.isEmpty() && Patterns.WEB_URL.matcher(url).matches()) {
                    progressBar.setVisibility(View.VISIBLE);
                    new ImportRecipeTask().execute(url);
                } else {
                    Toast.makeText(BrowseMealsActivity.this, "Please enter a valid URL", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class ImportRecipeTask extends AsyncTask<String, Void, Recipe> {

        @Override
        protected Recipe doInBackground(String... params) {
            String url = params[0];
            try {
                Document doc = Jsoup.connect(url).get();

                Element titleElement = doc.select("h1.recipe-title").first();  // Adjust the class accordingly
                String title = (titleElement != null) ? titleElement.text() : "No Title Found";

                Elements ingredientElements = doc.select(".ingredients-list li"); // Adjust the class accordingly
                List<String> ingredients = new ArrayList<>();
                for (Element ingredient : ingredientElements) {
                    ingredients.add(ingredient.text());
                }

                Elements instructionElements = doc.select(".instructions p"); // Adjust the class accordingly
                List<String> instructions = new ArrayList<>();
                for (Element instruction : instructionElements) {
                    instructions.add(instruction.text());
                }

                return new Recipe(title, ingredients, instructions);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Recipe recipe) {
            progressBar.setVisibility(View.GONE);  // Hide progress bar

            if (recipe != null) {
                StringBuilder details = new StringBuilder();
                details.append("Title: ").append(recipe.getTitle()).append("\n\nIngredients:\n");
                for (String ingredient : recipe.getIngredients()) {
                    details.append("- ").append(ingredient).append("\n");
                }
                details.append("\nInstructions:\n");
                for (String instruction : recipe.getInstructions()) {
                    details.append("- ").append(instruction).append("\n");
                }
                instructionsTextView.setText(details.toString());
            } else {
                instructionsTextView.setText("Failed to fetch or parse the recipe. Please check the URL.");
            }
        }
    }
}
