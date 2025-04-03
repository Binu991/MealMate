package com.example.mealmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddRecipeOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe_options);

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> {
            finish();
        });

        Button btnWeb = findViewById(R.id.buttonweb);
        btnWeb.setOnClickListener(view -> {
            Intent intent = new Intent(AddRecipeOptionsActivity.this, BrowseMealsActivity.class);
            startActivity(intent);
        });

        Button btnScratch = findViewById(R.id.buttonscratch);
        btnScratch.setOnClickListener(view -> {
            Intent intent = new Intent(AddRecipeOptionsActivity.this, AddRecipeActivity.class);
            startActivityForResult(intent, 100);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
