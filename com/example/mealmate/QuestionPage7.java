package com.example.mealmate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class QuestionPage7 extends Activity {

    private Button bttnBack, bttnContinue;
    private CheckBox cbYear, cbThreeMonths, cbMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionpage7);

        bttnBack = findViewById(R.id.bttnBack);
        bttnContinue = findViewById(R.id.bttnContinue);

        cbYear = findViewById(R.id.cbYear);
        cbThreeMonths = findViewById(R.id.cbThreeMonths);
        cbMonth = findViewById(R.id.cbMonth);

        CompoundButton.OnCheckedChangeListener checkboxListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setTextColor(isChecked ? Color.GRAY : Color.BLACK);
            }
        };
        cbYear.setOnCheckedChangeListener(checkboxListener);
        cbThreeMonths.setOnCheckedChangeListener(checkboxListener);
        cbMonth.setOnCheckedChangeListener(checkboxListener);

        bttnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(QuestionPage7.this, QuestionPage6.class);
                startActivity(backIntent);
            }
        });

        bttnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextIntent = new Intent(QuestionPage7.this, SignUp.class);
                startActivity(nextIntent);
            }
        });
    }
}
