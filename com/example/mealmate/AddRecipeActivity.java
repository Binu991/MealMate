package com.example.mealmate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mealmate.helper.RecipeDao;
import com.example.mealmate.model.Dish;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddRecipeActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;

    private ImageView recipeImage;
    private EditText recipeTitle, recipeInstructions;
    private Button chooseImageBtn, doneButton, addIngredientBtn;
    private LinearLayout ingredientsContainer;

    private Uri imageUri;
    private List<View> ingredientRows = new ArrayList<>();

    private final int KEY_INGREDIENT_NAME = R.id.key_ingredient_name;
    private final int KEY_INGREDIENT_QTY = R.id.key_ingredient_qty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        recipeImage = findViewById(R.id.recipeImage);
        recipeTitle = findViewById(R.id.recipeTitle);
        recipeInstructions = findViewById(R.id.recipeInstructions);
        chooseImageBtn = findViewById(R.id.chooseImageBtn);
        doneButton = findViewById(R.id.doneButton);
        addIngredientBtn = findViewById(R.id.addIngredientBtn);
        ingredientsContainer = findViewById(R.id.ingredientsContainer);

        chooseImageBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, PICK_IMAGE);
        });

        addIngredientRow();

        addIngredientBtn.setOnClickListener(v -> addIngredientRow());

        doneButton.setOnClickListener(v -> {
            String title = recipeTitle.getText().toString().trim();
            String instructions = recipeInstructions.getText().toString().trim();

            if (title.isEmpty() || instructions.isEmpty() || imageUri == null) {
                Toast.makeText(this, "Please fill in all fields and choose an image.", Toast.LENGTH_SHORT).show();
                return;
            }

            List<String> ingredientsList = new ArrayList<>();
            for (View row : ingredientRows) {
                EditText etName = (EditText) row.getTag(KEY_INGREDIENT_NAME);
                EditText etQty = (EditText) row.getTag(KEY_INGREDIENT_QTY);
                String name = etName.getText().toString().trim();
                String qty = etQty.getText().toString().trim();
                if (!name.isEmpty()) {
                    ingredientsList.add(qty.isEmpty() ? name : qty + " " + name);
                }
            }

            Dish newDish = new Dish(title, ingredientsList, List.of(instructions), imageUri);
            RecipeDao dao = new RecipeDao(this);
            dao.insertDish(newDish);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("dish", newDish);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private void addIngredientRow() {
        EditText nameField = new EditText(this);
        nameField.setHint("Ingredient Name");
        nameField.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2));

        EditText qtyField = new EditText(this);
        qtyField.setHint("Quantity");
        qtyField.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setPadding(0, 8, 0, 8);
        row.addView(nameField);
        row.addView(qtyField);

        row.setTag(KEY_INGREDIENT_NAME, nameField);
        row.setTag(KEY_INGREDIENT_QTY, qtyField);

        ingredientsContainer.addView(row);
        ingredientRows.add(row);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            getContentResolver().takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                recipeImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
