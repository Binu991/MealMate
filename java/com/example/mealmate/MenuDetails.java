package com.example.mealmate;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mealmate.R;

import java.util.ArrayList;

public class MenuDetails extends AppCompatActivity {

    private ImageView ivMenuImage;
    private TextView tvMenuName, tvIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_details);

        ivMenuImage = findViewById(R.id.ivMenuImage);
        tvMenuName = findViewById(R.id.tvMenuName);
        tvIngredients = findViewById(R.id.tvIngredients);

        Intent intent = getIntent();
        String menuName = intent.getStringExtra("menuName");
        ArrayList<String> menuItems = intent.getStringArrayListExtra("menuItems");
        String imagePath = intent.getStringExtra("imagePath");

        tvMenuName.setText(menuName);
        if (menuItems != null) {
            StringBuilder ingredients = new StringBuilder();
            for (String item : menuItems) {
                ingredients.append(item).append("\n");
            }
            tvIngredients.setText(ingredients.toString());
        }
        if (imagePath != null) {
            ivMenuImage.setImageURI(Uri.parse(imagePath));
        }
    }
}
