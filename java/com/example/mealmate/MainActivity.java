package com.example.mealmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ImageButton btnMenu, btnSearch, btnNotification;
    private FloatingActionButton fabAddItem;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        btnMenu = findViewById(R.id.btnMenu);
        btnSearch = findViewById(R.id.btnSearch);
        btnNotification = findViewById(R.id.btnNotification);
        fabAddItem = findViewById(R.id.fab_add_item);
        bottomNav = findViewById(R.id.bottomNav);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        btnMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        btnSearch.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Search.class));
        });

        btnNotification.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, Notifications.class));
        });

        fabAddItem.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddItemSelectionActivity.class);
            startActivity(intent);
        });

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home) {
                startActivity(new Intent(MainActivity.this, Home.class));
                return true;
            } else if (id == R.id.recipe) {
                startActivity(new Intent(MainActivity.this, CreateMeal.class));
                return true;
            } else if (id == R.id.grocery) {
                startActivity(new Intent(MainActivity.this, Grocery.class));
                return true;
            } else if (id == R.id.setting) {
                startActivity(new Intent(MainActivity.this, Settings.class));
                return true;
            }
            return false;
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }
}