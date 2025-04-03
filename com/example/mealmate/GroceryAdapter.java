package com.example.mealmate;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GroceryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private final List<Object> displayList = new ArrayList<>();
    private final Context context;
    private boolean showCheckbox = true;

    public GroceryAdapter(Context context, ArrayList<GroceryItem> groceryList) {
        this.context = context;
        organizeByCategory(groceryList);
    }

    public void setShowCheckbox(boolean show) {
        this.showCheckbox = show;
    }

    private void organizeByCategory(List<GroceryItem> groceryList) {
        displayList.clear();
        Map<String, List<GroceryItem>> groupedMap = new LinkedHashMap<>();

        for (GroceryItem item : groceryList) {
            if (item.getName() == null) continue;
            String category = item.getCategory();
            if (!groupedMap.containsKey(category)) {
                groupedMap.put(category, new ArrayList<>());
            }
            groupedMap.get(category).add(item);
        }

        for (Map.Entry<String, List<GroceryItem>> entry : groupedMap.entrySet()) {
            displayList.add(entry.getKey());
            displayList.addAll(entry.getValue());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (displayList.get(position) instanceof String) ? TYPE_HEADER : TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_header, parent, false);
            return new CategoryViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grocery, parent, false);
            return new GroceryViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            ((CategoryViewHolder) holder).bind((String) displayList.get(position));
        } else {
            ((GroceryViewHolder) holder).bind((GroceryItem) displayList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }

    public Object getItemAt(int position) {
        return displayList.get(position);
    }

    public void removeItem(int position) {
        displayList.remove(position);
        notifyItemRemoved(position);
    }

    public class GroceryViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName, tvQuantity, tvPrice, tvStore;
        CheckBox checkBoxBought;

        public GroceryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStore = itemView.findViewById(R.id.tvStore);
            checkBoxBought = itemView.findViewById(R.id.checkBoxBought);

            itemView.setOnLongClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && displayList.get(position) instanceof GroceryItem) {
                    GroceryItem item = (GroceryItem) displayList.get(position);
                    showEditDialog(item);
                }
                return true;
            });
        }

        public void bind(GroceryItem item) {
            tvItemName.setText(item.getName());

            if (item.getName() == null || item.getQuantity().isEmpty()) {
                tvQuantity.setVisibility(View.GONE);
            } else {
                tvQuantity.setVisibility(View.VISIBLE);
                tvQuantity.setText("Qty: " + item.getQuantity());
            }

            if (!item.getPrice().isEmpty()) {
                tvPrice.setVisibility(View.VISIBLE);
                tvPrice.setText("Rs. " + item.getPrice());
            } else {
                tvPrice.setVisibility(View.GONE);
            }

            if (!item.getStoreName().isEmpty()) {
                tvStore.setVisibility(View.VISIBLE);
                tvStore.setText(item.getStoreName());
            } else {
                tvStore.setVisibility(View.GONE);
            }

            if (showCheckbox) {
                checkBoxBought.setVisibility(View.VISIBLE);
                checkBoxBought.setOnCheckedChangeListener(null);
                checkBoxBought.setChecked(item.isSelected());
                checkBoxBought.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    item.setSelected(isChecked);
                    if (context instanceof GroceryActivity) {
                        ((GroceryActivity) context).updateShareButtonVisibility();
                    }
                });
            } else {
                checkBoxBought.setVisibility(View.GONE);
            }
        }

        private void showEditDialog(GroceryItem item) {
            View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_grocery_item, null);

            EditText etName = dialogView.findViewById(R.id.etEditName);
            EditText etQuantity = dialogView.findViewById(R.id.etEditQuantity);
            EditText etCategory = dialogView.findViewById(R.id.etEditCategory);
            EditText etPrice = dialogView.findViewById(R.id.etEditPrice);
            EditText etStore = dialogView.findViewById(R.id.etEditStore);

            etName.setText(item.getName());
            etQuantity.setText(item.getQuantity());
            etCategory.setText(item.getCategory());
            etPrice.setText(item.getPrice());
            etStore.setText(item.getStoreName());

            new AlertDialog.Builder(context)
                    .setTitle("Edit Item")
                    .setView(dialogView)
                    .setPositiveButton("Save", (dialog, which) -> {
                        item.setName(etName.getText().toString().trim());
                        item.setQuantity(etQuantity.getText().toString().trim());
                        item.setCategory(etCategory.getText().toString().trim());
                        item.setPrice(etPrice.getText().toString().trim());
                        item.setStoreName(etStore.getText().toString().trim());

                        GroceryDatabaseHelper dbHelper = new GroceryDatabaseHelper(context);
                        dbHelper.insertOrUpdateItem(item);
                        notifyDataSetChanged();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryHeader;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryHeader = itemView.findViewById(R.id.tvCategoryHeader);
        }

        public void bind(String category) {
            tvCategoryHeader.setText(category.toUpperCase());
        }
    }
}
