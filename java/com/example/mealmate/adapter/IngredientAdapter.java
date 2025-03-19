package com.example.mealmate.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.core.content.ContextCompat;

import com.example.mealmate.R;
import com.example.mealmate.model.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private List<Ingredient> ingredientList;

    public IngredientAdapter(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);

        holder.textViewName.setText(ingredient.getName());
        holder.textViewDescription.setText(ingredient.getDescription());
        holder.textViewQuantity.setText("Quantity: " + ingredient.getQuantity());
        holder.textViewPrice.setText("Price: $" + ingredient.getPrice());

        if (ingredient.getImageUri() != null && !ingredient.getImageUri().isEmpty()) {
            holder.imageViewItem.setImageURI(Uri.parse(ingredient.getImageUri()));
        } else {
            holder.imageViewItem.setImageResource(R.drawable.ic_launcher_foreground);
        }

        holder.btnShare.setOnClickListener(v -> {
            String shareText = "Product: " + ingredient.getName()
                    + "\nDescription: " + ingredient.getDescription()
                    + "\nQuantity: " + ingredient.getQuantity()
                    + "\nPrice: $" + ingredient.getPrice();

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

            v.getContext().startActivity(Intent.createChooser(shareIntent, "Share via"));
        });

        holder.btnPlus.setOnClickListener(v -> {
            int currentQuantity = ingredient.getQuantity();
            ingredient.setQuantity(currentQuantity + 1);
            holder.textViewQuantity.setText("Quantity: " + ingredient.getQuantity());

        });

        holder.btnMinus.setOnClickListener(v -> {
            int currentQuantity = ingredient.getQuantity();
            if (currentQuantity > 0) {
                ingredient.setQuantity(currentQuantity - 1);
                holder.textViewQuantity.setText("Quantity: " + ingredient.getQuantity());

            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public void updateList(List<Ingredient> newList) {
        this.ingredientList = newList;
        notifyDataSetChanged();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewDescription, textViewQuantity, textViewPrice;
        ImageView imageViewItem;

        ImageView btnShare, btnPlus, btnMinus;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageViewItem = itemView.findViewById(R.id.imageViewItem);

            btnShare = itemView.findViewById(R.id.btnShare);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
        }
    }
}
