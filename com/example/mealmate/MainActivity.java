package com.example.mealmate;

import android.Manifest;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.pm.PackageManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealmate.adapters.MealAdapter;
import com.example.mealmate.model.Meal;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private LinearLayout beforeMealContainer;
    private TextView textTitleBefore, textSubTitleBefore;
    private Button buildFirstMealPlanButton;

    private LinearLayout afterMealContainer;
    private TextView textTitleAfter, textSubTitleAfter;

    private LinearLayout bottomButtonsContainer;
    private Button startMealPlanButton;
    private Button manageWeeklyRecipeButton;

    private RecyclerView rvMealList;
    private ArrayList<Meal> mealList;
    private MealAdapter mealAdapter;

    private static final int REQUEST_MEAL_PLAN = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        beforeMealContainer = findViewById(R.id.beforeMealContainer);
        afterMealContainer = findViewById(R.id.afterMealContainer);
        bottomButtonsContainer = findViewById(R.id.bottomButtonsContainer);

        textTitleBefore = findViewById(R.id.textTitleBefore);
        textSubTitleBefore = findViewById(R.id.textSubTitleBefore);
        buildFirstMealPlanButton = findViewById(R.id.buildFirstMealPlanButton);

        textTitleAfter = findViewById(R.id.textTitleAfter);
        textSubTitleAfter = findViewById(R.id.textSubTitleAfter);

        startMealPlanButton = findViewById(R.id.startMealPlanButton);
        manageWeeklyRecipeButton = findViewById(R.id.manageWeeklyRecipeButton);

        rvMealList = findViewById(R.id.rvMealList);
        mealList = new ArrayList<>();
        mealAdapter = new MealAdapter(mealList, meal -> {});
        rvMealList.setLayoutManager(new LinearLayoutManager(this));
        rvMealList.setAdapter(mealAdapter);
        rvMealList.setVisibility(View.GONE);

        beforeMealContainer.setVisibility(View.VISIBLE);
        afterMealContainer.setVisibility(View.GONE);
        bottomButtonsContainer.setVisibility(View.GONE);

        buildFirstMealPlanButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MealPlanScreenActivity.class);
            startActivityForResult(intent, REQUEST_MEAL_PLAN);
        });

        startMealPlanButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MealPlanScreenActivity.class);
            startActivityForResult(intent, REQUEST_MEAL_PLAN);
        });

        manageWeeklyRecipeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WeeklyRecipeActivity.class);
            startActivity(intent);
        });

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.action_notifications) {
                Intent intent = new Intent(MainActivity.this, NotificationSettingsActivity.class);
                startActivity(intent);
                return true;
            } else if (id == R.id.action_search) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
            }
            return false;
        });

        drawerLayout = findViewById(R.id.drawer_layout);
        topAppBar.setNavigationOnClickListener(v -> drawerLayout.open());

        NavigationView navView = findViewById(R.id.navigation_view);
        navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_recipes) {
                startActivity(new Intent(MainActivity.this, BrowseMealsActivity.class));
            } else if (id == R.id.nav_grocery) {
                startActivity(new Intent(MainActivity.this, GroceryActivity.class));
            } else if (id == R.id.nav_plan) {
                startActivity(new Intent(MainActivity.this, MealPlanScreenActivity.class));
            } else if (id == R.id.nav_share) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out MealMate app!");
                startActivity(Intent.createChooser(shareIntent, "Share MealMate"));
            } else if (id == R.id.nav_about) {
                Toast.makeText(MainActivity.this, "About MealMate", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_logout) {
                finishAffinity();
            }
            drawerLayout.close();
            return true;
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_meals) {
                Intent intent = new Intent(MainActivity.this, MealPlanScreenActivity.class);
                startActivityForResult(intent, REQUEST_MEAL_PLAN);
                return true;
            } else if (id == R.id.nav_grocery) {
                startActivity(new Intent(MainActivity.this, GroceryActivity.class));
                return true;
            } else if (id == R.id.nav_settings) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            }
            return false;
        });

        attachSwipeGesture();
    }

    private void attachSwipeGesture() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            private Paint paint = new Paint();

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    if (dX > 0) {
                        paint.setColor(Color.RED);
                        c.drawRect(itemView.getLeft(), itemView.getTop(),
                                itemView.getLeft() + dX, itemView.getBottom(), paint);
                    } else if (dX < 0) {
                        paint.setColor(Color.GREEN);
                        c.drawRect(itemView.getRight() + dX, itemView.getTop(),
                                itemView.getRight(), itemView.getBottom(), paint);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.RIGHT) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Delete Meal")
                            .setMessage("Are you sure you want to delete this meal plan from today's list?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                mealList.remove(position);
                                mealAdapter.notifyItemRemoved(position);
                            })
                            .setNegativeButton("No", (dialog, which) -> {
                                mealAdapter.notifyItemChanged(position);
                            })
                            .setCancelable(false)
                            .show();
                } else if (direction == ItemTouchHelper.LEFT) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Cooking Done")
                            .setMessage("Are you done with cooking?")
                            .setPositiveButton("Yes", (dialog, which) -> {
                                mealList.remove(position);
                                Toast.makeText(MainActivity.this, "Cooked - Enjoy your meal!", Toast.LENGTH_SHORT).show();
                                mealAdapter.notifyItemRemoved(position);
                            })
                            .setNegativeButton("No", (dialog, which) -> {
                                mealAdapter.notifyItemChanged(position);
                            })
                            .setCancelable(false)
                            .show();
                }
            }
        };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(rvMealList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MEAL_PLAN && resultCode == RESULT_OK && data != null) {
            String mealNameStr = data.getStringExtra("meal_name");
            String imageUriString = data.getStringExtra("image_uri");
            int imageResId = data.getIntExtra("image_res_id", -1);

            Log.d("MainActivity", "Meal name: " + mealNameStr);
            Log.d("MainActivity", "Received imageUri: " + imageUriString);
            Log.d("MainActivity", "Received imageResId: " + imageResId);

            Uri uri = null;
            if (imageUriString != null && !imageUriString.isEmpty()) {
                try {
                    uri = Uri.parse(imageUriString);
                    getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } catch (Exception e) {
                    Log.e("MainActivity", "Failed to parse or persist image URI", e);
                }
            }

            Meal newMeal;
            if (uri != null) {
                newMeal = new Meal(mealNameStr, uri, "Your ingredients", "Your instructions");
            } else if (imageResId != -1) {
                newMeal = new Meal(mealNameStr, imageResId);
            } else {
                newMeal = new Meal(mealNameStr, Uri.EMPTY, "Your ingredients", "Your instructions");
            }

            beforeMealContainer.setVisibility(View.GONE);
            afterMealContainer.setVisibility(View.VISIBLE);
            bottomButtonsContainer.setVisibility(View.VISIBLE);
            rvMealList.setVisibility(View.VISIBLE);

            textTitleAfter.setText("Meal Plan");
            String today = new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date());
            textSubTitleAfter.setText("Today: " + today);

            mealList.add(newMeal);
            mealAdapter.notifyDataSetChanged();

            startMealPlanButton.setText("Start Your Next Meal Plan");
            Toast.makeText(this, "Meal saved: " + mealNameStr, Toast.LENGTH_SHORT).show();
        }
    }
}
