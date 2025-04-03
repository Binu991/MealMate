package com.example.mealmate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Button btnGetStarted = findViewById(R.id.btnGetStarted);
        Button btnSignIn = findViewById(R.id.btnSignIn);

        btnGetStarted.setOnClickListener(v -> {
            Intent intent = new Intent(SplashScreen.this, SecondPage.class);
            startActivity(intent);
        });

        btnSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(SplashScreen.this, LogIn.class);
            startActivity(intent);
        });
    }
}
