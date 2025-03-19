package com.example.mealmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class EditItem extends AppCompatActivity {

    private EditText editTextName, editTextDescription, editTextQuantity, editTextPrice;
    private ImageView imageViewItem;
    private Button btnSaveChanges;

    private int position;
    private String imageUri = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        editTextName = findViewById(R.id.editTextName);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        editTextPrice = findViewById(R.id.editTextPrice);
        imageViewItem = findViewById(R.id.imageViewItem);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        int quantity = intent.getIntExtra("quantity", 0);
        double price = intent.getDoubleExtra("price", 0.0);
        imageUri = intent.getStringExtra("imageUri");

        editTextName.setText(name);
        editTextDescription.setText(description);
        editTextQuantity.setText(String.valueOf(quantity));
        editTextPrice.setText(String.valueOf(price));
        if (imageUri != null && !imageUri.isEmpty()) {
            imageViewItem.setImageURI(Uri.parse(imageUri));
        }

        btnSaveChanges.setOnClickListener(v -> {
            String newName = editTextName.getText().toString().trim();
            String newDescription = editTextDescription.getText().toString().trim();
            int newQuantity = 0;
            double newPrice = 0.0;
            try {
                newQuantity = Integer.parseInt(editTextQuantity.getText().toString().trim());
            } catch (NumberFormatException e) { /* ignore or handle */ }
            try {
                newPrice = Double.parseDouble(editTextPrice.getText().toString().trim());
            } catch (NumberFormatException e) { /* ignore or handle */ }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("position", position);
            resultIntent.putExtra("name", newName);
            resultIntent.putExtra("description", newDescription);
            resultIntent.putExtra("quantity", newQuantity);
            resultIntent.putExtra("price", newPrice);
            resultIntent.putExtra("imageUri", imageUri);

            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}
