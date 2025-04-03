package com.example.mealmate.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mealmate.R;
import com.example.mealmate.model.Meal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealViewHolder> {

    public interface OnMealClickListener {
        void onMealClick(Meal meal);
    }

    private ArrayList<Meal> mealList;
    private ArrayList<Meal> fullList;
    private Context context;
    private OnMealClickListener listener;

    public MealAdapter(ArrayList<Meal> mealList, OnMealClickListener listener) {
        this.mealList = mealList;
        this.fullList = new ArrayList<>(mealList);
        this.listener = listener;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_meal, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        Meal meal = mealList.get(position);
        holder.titleText.setText(meal.getName());

        if (meal.getImageUri() != null) {
            String uriString = meal.getImageUri().toString();
            Log.d("MealAdapter", "Loading image URI: " + uriString);

            if (uriString.contains("com.google.android.apps.photos.contentprovider")) {
                holder.imageView.setImageResource(R.drawable.placeholder_image);
            } else {
                Glide.with(context)
                        .load(meal.getImageUri())
                        .placeholder(R.drawable.placeholder_image)
                        .error(R.drawable.placeholder_image)
                        .into(holder.imageView);
            }

        } else if (meal.getImageResId() != -1) {
            Glide.with(context)
                    .load(meal.getImageResId())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.placeholder_image)
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.placeholder_image);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMealClick(meal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    public void filter(String text) {
        mealList.clear();
        if (text.isEmpty()) {
            mealList.addAll(fullList);
        } else {
            text = text.toLowerCase();
            for (Meal meal : fullList) {
                if (meal.getName().toLowerCase().contains(text)) {
                    mealList.add(meal);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void updateMeals(ArrayList<Meal> newMeals) {
        fullList.clear();
        fullList.addAll(newMeals);
        mealList.clear();
        mealList.addAll(newMeals);

        Collections.sort(mealList, new Comparator<Meal>() {
            @Override
            public int compare(Meal m1, Meal m2) {
                return Long.compare(m2.getDateAdded(), m1.getDateAdded());
            }
        });

        notifyDataSetChanged();
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleText;

        MealViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.mealImage);
            titleText = itemView.findViewById(R.id.mealTitle);
        }
    }
}
