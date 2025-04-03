package com.example.mealmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SecondPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondpage);

        Button btnBack = findViewById(R.id.btnBack);
        Button btnContinue = findViewById(R.id.btnContinue);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondPage.this, SplashScreen.class);
                startActivity(intent);
                finish();
            }
        });


        btnContinue.setOnClickListener(v -> {
            Intent intent = new Intent(SecondPage.this, ThirdPage.class);
            startActivity(intent);
            finish();
        });
    }
}
