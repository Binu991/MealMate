package com.example.mealmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ThirdPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_thirdpage);

        Button btnBack1 = findViewById(R.id.btnBack1);
        Button btnContinue1 = findViewById(R.id.btnContinue1);

        btnBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThirdPage.this, SecondPage.class);
                startActivity(intent);
                finish();
            }
        });

        btnContinue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThirdPage.this, FourthPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
