package com.example.mealmate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mealmate.adapter.IngredientAdapter;
import com.example.mealmate.model.Ingredient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Grocery extends AppCompatActivity {

    private static final int REQUEST_CODE_ADD_ITEM = 100;
    private static final int REQUEST_CODE_EDIT_ITEM = 200;

    private RecyclerView recyclerViewItems;
    private FloatingActionButton fabAddItem;

    private ArrayList<Ingredient> ingredientList = new ArrayList<>();
    private IngredientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

        recyclerViewItems = findViewById(R.id.recyclerViewItems);
        fabAddItem = findViewById(R.id.fabAddItem);
        adapter = new IngredientAdapter(ingredientList);
        recyclerViewItems.setAdapter(adapter);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    new AlertDialog.Builder(Grocery.this)
                            .setTitle("Delete Item")
                            .setMessage("Are you sure you want to delete this item?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                ingredientList.remove(position);
                                adapter.notifyItemRemoved(position);
                            })
                            .setNegativeButton("No", (dialog, which) -> {
                                adapter.notifyItemChanged(position);
                            })
                            .show();

                } else if (direction == ItemTouchHelper.RIGHT) {
                    Ingredient item = ingredientList.get(position);
                    item.setPurchased(true);
                    adapter.notifyItemChanged(position);
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewItems);

        adapter.setOnItemClickListener(new IngredientAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Ingredient clickedItem = ingredientList.get(position);

                Intent intent = new Intent(Grocery.this, EditItem.class);
                intent.putExtra("position", position);
                intent.putExtra("name", clickedItem.getName());
                intent.putExtra("description", clickedItem.getDescription());
                intent.putExtra("quantity", clickedItem.getQuantity());
                intent.putExtra("price", clickedItem.getPrice());
                intent.putExtra("imageUri", clickedItem.getImageUri());

                startActivityForResult(intent, REQUEST_CODE_EDIT_ITEM);
            }
        });

        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Grocery.this, AddItem.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_ITEM);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_ITEM && resultCode == RESULT_OK) {
            if (data != null) {
                String name = data.getStringExtra("name");
                String description = data.getStringExtra("description");
                int quantity = data.getIntExtra("quantity", 0);
                double price = data.getDoubleExtra("price", 0.0);
                String imageUri = data.getStringExtra("imageUri");

                Ingredient newItem = new Ingredient(name, description, quantity, price, imageUri);
                ingredientList.add(newItem);

                adapter.notifyDataSetChanged();
            }
        }

        else if (requestCode == REQUEST_CODE_EDIT_ITEM && resultCode == RESULT_OK) {
            if (data != null) {
                int pos = data.getIntExtra("position", -1);
                if (pos != -1) {
                    String updatedName = data.getStringExtra("name");
                    String updatedDescription = data.getStringExtra("description");
                    int updatedQuantity = data.getIntExtra("quantity", 0);
                    double updatedPrice = data.getDoubleExtra("price", 0.0);
                    String updatedImageUri = data.getStringExtra("imageUri");

                    Ingredient item = ingredientList.get(pos);
                    item.setName(updatedName);
                    item.setDescription(updatedDescription);
                    item.setQuantity(updatedQuantity);
                    item.setPrice(updatedPrice);
                    item.setImageUri(updatedImageUri);

                    adapter.notifyItemChanged(pos);
                }
            }
        }
    }
}
