package com.example.mealmate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.mealmate.menumanager.Menu;
import com.example.mealmate.menumanager.MenuDatabase;

import java.util.ArrayList;

public class CreateMeal extends AppCompatActivity {

    private static final int REQUEST_IMAGE_PICK = 1001; // 1) A constant for the image picker

    private EditText etMenuName, etNewMenuItem;
    private Button btnAddMenuItem, btnSaveMenu;

    // 2) Add a Button for selecting the image
    private Button btnSelectImage;

    // 3) Add an ImageView to preview the selected image
    private ImageView ivSelectedImage;

    private ListView lvMenuItems;
    private ArrayList<String> menuItemsList;
    private ArrayAdapter<String> menuItemsAdapter;
    private MenuDatabase db;

    // 4) We'll store the selected image URI as a String here
    private String selectedImagePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_meal);

        // Existing code
        etMenuName = findViewById(R.id.etMenuName);
        etNewMenuItem = findViewById(R.id.etNewMenuItem);
        btnAddMenuItem = findViewById(R.id.btnAddMenuItem);
        btnSaveMenu = findViewById(R.id.btnSaveMenu);
        lvMenuItems = findViewById(R.id.lvMenuItems);

        // 5) Find the newly added views in your XML
        ivSelectedImage = findViewById(R.id.ivSelectedImage);
        btnSelectImage = findViewById(R.id.btnSelectImage);

        menuItemsList = new ArrayList<>();
        menuItemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menuItemsList);
        lvMenuItems.setAdapter(menuItemsAdapter);

        // Room DB
        db = Room.databaseBuilder(
                        getApplicationContext(),
                        MenuDatabase.class,
                        "menu_database"
                )
                .allowMainThreadQueries() // For demo only
                .build();

        // "Add" menu item to the list
        btnAddMenuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String menuItem = etNewMenuItem.getText().toString().trim();
                if (!menuItem.isEmpty()) {
                    menuItemsList.add(menuItem);
                    menuItemsAdapter.notifyDataSetChanged();
                    etNewMenuItem.setText(""); // Clear input
                }
            }
        });

        // 6) "Select Image" button logic
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Launch an intent to pick an image from the gallery
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE_PICK);
            }
        });

        // "Save Menu"
        btnSaveMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String menuName = etMenuName.getText().toString().trim();
                if (menuName.isEmpty()) {
                    etMenuName.setError("Please enter a menu name");
                    return;
                }

                // 7) Create a Menu object with the image path
                Menu menu = new Menu(menuName, menuItemsList, selectedImagePath);

                db.menuDao().insert(menu);

                // Go to Home activity
                Intent intent = new Intent(CreateMeal.this, Home.class);
                startActivity(intent);
            }
        });
    }

    // 8) Handle the result of image picking
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                // Convert URI to string
                selectedImagePath = imageUri.toString();
                // Show the image in the ImageView
                ivSelectedImage.setImageURI(imageUri);
            }
        }
    }
}
