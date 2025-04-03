package com.example.mealmate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class QuestionPage6 extends Activity {

    private Button btnBack9, btnContinue9;
    private CheckBox cbYes, cbNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionpage6);

        btnBack9 = findViewById(R.id.btnBack9);
        btnContinue9 = findViewById(R.id.btnContinue9);

        cbYes = findViewById(R.id.cbYes);
        cbNo = findViewById(R.id.cbNo);

        CompoundButton.OnCheckedChangeListener checkboxListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setTextColor(isChecked ? Color.GRAY : Color.BLACK);
            }
        };
        cbYes.setOnCheckedChangeListener(checkboxListener);
        cbNo.setOnCheckedChangeListener(checkboxListener);

        btnBack9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(QuestionPage6.this, QuestionPage5.class);
                startActivity(backIntent);
            }
        });

        btnContinue9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextIntent = new Intent(QuestionPage6.this, QuestionPage7.class);
                startActivity(nextIntent);
            }
        });
    }
}
