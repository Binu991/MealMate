package com.example.mealmate.adapters;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealmate.R;
import com.example.mealmate.model.Meal;
import com.example.mealmate.model.WeeklyDay;

import java.util.List;

public class WeeklyRecipeAdapter extends RecyclerView.Adapter<WeeklyRecipeAdapter.WeeklyViewHolder> {

    public interface OnDayClickListener {
        void onAddMealClicked(WeeklyDay day);
    }

    private List<WeeklyDay> weeklyDays;
    private OnDayClickListener listener;

    public WeeklyRecipeAdapter(List<WeeklyDay> weeklyDays, OnDayClickListener listener) {
        this.weeklyDays = weeklyDays;
        this.listener = listener;
    }

    @NonNull
    @Override
    public WeeklyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weekly_day, parent, false);
        return new WeeklyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeeklyViewHolder holder, int position) {
        WeeklyDay day = weeklyDays.get(position);
        holder.tvDay.setText(day.getDayName());
        holder.tvDate.setText(day.getDate());

        holder.btnAddMeal.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddMealClicked(day);
            }
        });

        holder.mealContainer.removeAllViews();

        for (Meal meal : day.getMeals()) {
            View mealView = LayoutInflater.from(holder.itemView.getContext())
                    .inflate(R.layout.item_meal_horizontal, holder.mealContainer, false);

            ImageView mealImage = mealView.findViewById(R.id.ivMealImage);
            TextView mealName = mealView.findViewById(R.id.tvMealTitle);
            ImageView btnDelete = mealView.findViewById(R.id.btnDeleteMeal);

            mealName.setText(meal.getName());

            if (meal.isFromUri() && meal.getImageUri() != null) {
                Glide.with(mealView.getContext()).load(meal.getImageUri()).into(mealImage);
            } else if (meal.getImageResId() != 0) {
                mealImage.setImageResource(meal.getImageResId());
            } else {
                mealImage.setImageResource(R.drawable.placeholder_image);
            }

            btnDelete.setOnClickListener(view -> {
                new AlertDialog.Builder(holder.itemView.getContext())
                        .setTitle("Delete Meal")
                        .setMessage("Are you sure you want to delete this meal?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            day.getMeals().remove(meal);
                            notifyDataSetChanged();
                        })
                        .setNegativeButton("No", null)
                        .show();
            });

            holder.mealContainer.addView(mealView);
        }
    }

    @Override
    public int getItemCount() {
        return weeklyDays.size();
    }

    public static class WeeklyViewHolder extends RecyclerView.ViewHolder {
        TextView tvDay, tvDate;
        Button btnAddMeal;
        LinearLayout mealContainer;

        public WeeklyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvDate = itemView.findViewById(R.id.tvDate);
            btnAddMeal = itemView.findViewById(R.id.btnAddMeal);
            mealContainer = itemView.findViewById(R.id.mealContainer);
        }
    }
}
