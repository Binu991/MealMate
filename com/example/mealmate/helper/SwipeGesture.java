package com.example.mealmate.helper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SwipeGesture extends ItemTouchHelper.SimpleCallback {

    private ArrayList<String> items;
    private RecyclerView.Adapter<?> adapter;
    private Context context;

    public SwipeGesture(Context context, ArrayList<String> items, RecyclerView.Adapter<?> adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.context = context;
        this.items = items;
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // This will be overridden in GroceryActivity
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;
        ColorDrawable background = new ColorDrawable();

        if (dX > 0) {
            background.setColor(Color.GREEN);
        } else {
            background.setColor(Color.RED);
        }

        background.setBounds(itemView.getLeft(), itemView.getTop(),
                itemView.getRight(), itemView.getBottom());
        background.draw(c);

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
