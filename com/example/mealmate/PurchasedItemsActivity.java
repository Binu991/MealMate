package com.example.mealmate;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PurchasedItemsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GroceryAdapter adapter;
    private GroceryDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchased_items);

        recyclerView = findViewById(R.id.recyclerPurchased);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new GroceryDatabaseHelper(this);

        List<GroceryItem> purchasedItems = dbHelper.getPurchasedItems();
        adapter = new GroceryAdapter(this, new ArrayList<>(purchasedItems));
        adapter.setShowCheckbox(false);

        recyclerView.setAdapter(adapter);
    }
}
