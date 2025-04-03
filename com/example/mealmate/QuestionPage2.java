package com.example.mealmate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class QuestionPage2 extends Activity {

    private Button btnBack5, btnContinue5;
    private CheckBox cbOmnivore, cbPescoPollo, cbPescetarian, cbVegetarian, cbVegan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionpage2);

        btnBack5 = findViewById(R.id.btnBack5);
        btnContinue5 = findViewById(R.id.btnContinue5);

        cbOmnivore = findViewById(R.id.cbOmnivore);
        cbPescoPollo = findViewById(R.id.cbPescoPollo);
        cbPescetarian = findViewById(R.id.cbPescetarian);
        cbVegetarian = findViewById(R.id.cbVegetarian);
        cbVegan = findViewById(R.id.cbVegan);

        CompoundButton.OnCheckedChangeListener checkboxListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setTextColor(isChecked ? Color.GRAY : Color.BLACK);
            }
        };
        cbOmnivore.setOnCheckedChangeListener(checkboxListener);
        cbPescoPollo.setOnCheckedChangeListener(checkboxListener);
        cbPescetarian.setOnCheckedChangeListener(checkboxListener);
        cbVegetarian.setOnCheckedChangeListener(checkboxListener);
        cbVegan.setOnCheckedChangeListener(checkboxListener);

        btnBack5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(QuestionPage2.this, QuestionPage.class);
                startActivity(backIntent);
            }
        });

        btnContinue5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextIntent = new Intent(QuestionPage2.this, QuestionPage3.class);
                startActivity(nextIntent);
            }
        });
    }
}
