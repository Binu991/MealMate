package com.example.mealmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CollectRecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_recipes);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            finish();
        });

        Button btnAddARecipe = findViewById(R.id.btnAddARecipe);
        btnAddARecipe.setOnClickListener(view -> {
            Intent intent = new Intent(CollectRecipesActivity.this, AddRecipeOptionsActivity.class);
            startActivityForResult(intent, 100);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
