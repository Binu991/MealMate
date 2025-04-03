package com.example.mealmate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class QuestionPage3 extends Activity {

    private Button btnBack6, btnContinue6;
    private CheckBox cbBreakfast, cbLunch, cbDinner, cbSnacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionpage3);

        btnBack6 = findViewById(R.id.btnBack6);
        btnContinue6 = findViewById(R.id.btnContinue6);

        cbBreakfast = findViewById(R.id.cbBreakfast);
        cbLunch = findViewById(R.id.cbLunch);
        cbDinner = findViewById(R.id.cbDinner);
        cbSnacks = findViewById(R.id.cbSnacks);

        CompoundButton.OnCheckedChangeListener checkboxListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setTextColor(isChecked ? Color.GRAY : Color.BLACK);
            }
        };
        cbBreakfast.setOnCheckedChangeListener(checkboxListener);
        cbLunch.setOnCheckedChangeListener(checkboxListener);
        cbDinner.setOnCheckedChangeListener(checkboxListener);
        cbSnacks.setOnCheckedChangeListener(checkboxListener);


        btnBack6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(QuestionPage3.this, QuestionPage2.class);
                startActivity(backIntent);
            }
        });

        btnContinue6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextIntent = new Intent(QuestionPage3.this, QuestionPage4.class);
                startActivity(nextIntent);
            }
        });
    }
}
