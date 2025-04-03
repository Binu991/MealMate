package com.example.mealmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class FourthPage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourthpage);

        Button btnBack2 = findViewById(R.id.btnBack2);
        Button btnContinue2 = findViewById(R.id.btnContinue2);


        btnBack2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FourthPage.this, ThirdPage.class);
                startActivity(intent);
                finish();
            }
        });

        btnContinue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FourthPage.this, FifthPage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
