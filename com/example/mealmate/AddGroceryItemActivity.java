package com.example.mealmate;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddGroceryItemActivity extends AppCompatActivity {

    private EditText etName, etQuantity, etCategory;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery_item);

        etName = findViewById(R.id.etName);
        etQuantity = findViewById(R.id.etQuantity);
        etCategory = findViewById(R.id.etCategory);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String quantityStr = etQuantity.getText().toString().trim();
            String category = etCategory.getText().toString().trim();

            if (name.isEmpty() || quantityStr.isEmpty()) {
                Toast.makeText(this, "Please enter name and quantity", Toast.LENGTH_SHORT).show();
                return;
            }

            GroceryItem item = new GroceryItem(name, quantityStr, category.isEmpty() ? "Uncategorized" : category);
            GroceryDatabaseHelper dbHelper = new GroceryDatabaseHelper(this);
            dbHelper.insertOrUpdateItem(item);

            Toast.makeText(this, "Item added to grocery list!", Toast.LENGTH_SHORT).show();
            finish();
        });


    }
}
