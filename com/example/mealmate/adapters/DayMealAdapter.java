package com.example.mealmate.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealmate.R;
import com.example.mealmate.model.Meal;

import java.util.List;

public class DayMealAdapter extends RecyclerView.Adapter<DayMealAdapter.DayMealViewHolder> {

    public interface OnMealClickListener {
        void onEditClicked(Meal meal);
        void onDeleteClicked(Meal meal);
    }

    private List<Meal> meals;
    private OnMealClickListener listener;

    public DayMealAdapter(List<Meal> meals, OnMealClickListener listener) {
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DayMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meal_horizontal, parent, false);
        return new DayMealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayMealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.title.setText(meal.getName());

        if (meal.getImageUri() != null) {
            Glide.with(holder.itemView.getContext())
                    .load(meal.getImageUri())
                    .placeholder(R.drawable.placeholder_image)
                    .into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.placeholder_image);
        }

        holder.itemView.setOnLongClickListener(v -> {
            if (listener != null) listener.onEditClicked(meal);
            return true;
        });

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteClicked(meal);
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public static class DayMealViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;

        public DayMealViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.ivMealImage);
            title = itemView.findViewById(R.id.tvMealTitle);
        }
    }
}
