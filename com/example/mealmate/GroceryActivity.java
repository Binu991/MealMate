package com.example.mealmate;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GroceryActivity extends AppCompatActivity {

    private static final int PICK_CONTACT_REQUEST = 1;
    private RecyclerView rvGroceryList;
    private GroceryAdapter groceryAdapter;
    private ArrayList<GroceryItem> groceryList;
    private FloatingActionButton fabAddItem;
    private Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

        rvGroceryList = findViewById(R.id.rvGroceryList);
        fabAddItem = findViewById(R.id.fabAddItem);
        btnShare = findViewById(R.id.btnShare);

        groceryList = new ArrayList<>();
        groceryAdapter = new GroceryAdapter(this, groceryList);

        rvGroceryList.setLayoutManager(new LinearLayoutManager(this));
        rvGroceryList.setAdapter(groceryAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Object obj = groceryAdapter.getItemAt(position);

                        if (!(obj instanceof GroceryItem)) {
                            groceryAdapter.notifyItemChanged(position);
                            return;
                        }

                        GroceryItem item = (GroceryItem) obj;
                        GroceryDatabaseHelper dbHelper = new GroceryDatabaseHelper(GroceryActivity.this);

                        if (direction == ItemTouchHelper.RIGHT) {
                            new AlertDialog.Builder(GroceryActivity.this)
                                    .setTitle("Delete")
                                    .setMessage("Are you sure you want to delete this item?")
                                    .setPositiveButton("Yes", (dialog, which) -> {
                                        dbHelper.deleteItem(item.getName());
                                        groceryAdapter.removeItem(position);
                                    })
                                    .setNegativeButton("No", (dialog, which) -> groceryAdapter.notifyItemChanged(position))
                                    .setCancelable(false)
                                    .show();
                        } else if (direction == ItemTouchHelper.LEFT) {
                            item.setPurchased(true);
                            dbHelper.insertOrUpdateItem(item);
                            groceryAdapter.removeItem(position);
                            Toast.makeText(GroceryActivity.this, item.getName() + " marked as purchased!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        itemTouchHelper.attachToRecyclerView(rvGroceryList);

        loadGroceryItems();

        fabAddItem.setOnClickListener(view -> {
            Intent intent = new Intent(GroceryActivity.this, AddGroceryItemActivity.class);
            startActivity(intent);
        });

        btnShare.setOnClickListener(v -> {
            String messageContent = createGroceryListMessage();
            showContactOrManualEntryDialog(messageContent);
        });
    }

    private void loadGroceryItems() {
        GroceryDatabaseHelper dbHelper = new GroceryDatabaseHelper(this);
        List<GroceryItem> itemsFromDB = dbHelper.getAllItems();

        groceryList.clear();

        Map<String, List<GroceryItem>> groupedMap = new LinkedHashMap<>();
        for (GroceryItem item : itemsFromDB) {
            if (item.isPurchased()) continue;

            String category = item.getCategory();
            if (!groupedMap.containsKey(category)) {
                groupedMap.put(category, new ArrayList<>());
            }
            groupedMap.get(category).add(item);
        }

        for (Map.Entry<String, List<GroceryItem>> entry : groupedMap.entrySet()) {
            GroceryItem header = new GroceryItem();
            header.setName(null);
            header.setCategory(entry.getKey());
            groceryList.add(header);
            groceryList.addAll(entry.getValue());
        }

        groceryAdapter = new GroceryAdapter(this, groceryList);
        rvGroceryList.setAdapter(groceryAdapter);

        updateShareButtonVisibility();
    }

    public void updateShareButtonVisibility() {
        boolean visible = false;
        for (GroceryItem item : groceryList) {
            if (item.getName() != null && item.isSelected()) {
                visible = true;
                break;
            }
        }
        btnShare.setVisibility(visible ? Button.VISIBLE : Button.GONE);
    }

    private String createGroceryListMessage() {
        StringBuilder content = new StringBuilder("Grocery List:\n");
        for (GroceryItem item : groceryList) {
            if (item.getName() != null && item.isSelected()) {
                content.append("- ").append(item.getName())
                        .append(" (Qty: ").append(item.getQuantity()).append(")");
                if (!item.getPrice().isEmpty()) {
                    content.append(", Price: Rs.").append(item.getPrice());
                }
                if (!item.getStoreName().isEmpty()) {
                    content.append(", Store: ").append(item.getStoreName());
                }
                content.append("\n");
            }
        }
        return content.toString();
    }

    private void showContactOrManualEntryDialog(String messageContent) {
        String[] options = {"Choose from Contacts", "Enter Number Manually"};
        new AlertDialog.Builder(this)
                .setTitle("Select Contact")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        Intent pickContactIntent = new Intent(Intent.ACTION_PICK,
                                android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
                    } else {
                        showManualEntryDialog(messageContent);
                    }
                })
                .show();
    }

    private void showManualEntryDialog(String messageContent) {
        final EditText input = new EditText(this);
        input.setHint("Enter phone number");

        new AlertDialog.Builder(this)
                .setTitle("Enter Phone Number")
                .setView(input)
                .setPositiveButton("Send", (dialog, which) -> {
                    String phoneNumber = input.getText().toString().trim();
                    if (!phoneNumber.isEmpty()) {
                        sendSms(phoneNumber, messageContent);
                    } else {
                        Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void sendSms(String phoneNumber, String messageContent) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setData(Uri.parse("smsto:" + phoneNumber));
        smsIntent.putExtra("sms_body", messageContent);
        startActivity(smsIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri contactUri = data.getData();
            String[] projection = { android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER };
            try (Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int numberIndex = cursor.getColumnIndex(android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String phoneNumber = cursor.getString(numberIndex);
                    String messageContent = createGroceryListMessage();
                    sendSms(phoneNumber, messageContent);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadGroceryItems();
    }
}
