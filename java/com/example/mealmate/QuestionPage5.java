package com.example.mealmate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class QuestionPage5 extends Activity {

    private Button btnBack8, btnContinue8;
    private CheckBox cbOne, cbTwo, cbThree, cbFour, cbFive, cbMoreThanFive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionpage5);

        btnBack8 = findViewById(R.id.btnBack8);
        btnContinue8 = findViewById(R.id.btnContinue8);

        cbOne = findViewById(R.id.cbOne);
        cbTwo = findViewById(R.id.cbTwo);
        cbThree = findViewById(R.id.cbThree);
        cbFour = findViewById(R.id.cbFour);
        cbFive = findViewById(R.id.cbFive);
        cbMoreThanFive = findViewById(R.id.cbMoreThanFive);

        CompoundButton.OnCheckedChangeListener checkboxListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setTextColor(isChecked ? Color.GRAY : Color.BLACK);
            }
        };
        cbOne.setOnCheckedChangeListener(checkboxListener);
        cbTwo.setOnCheckedChangeListener(checkboxListener);
        cbThree.setOnCheckedChangeListener(checkboxListener);
        cbFour.setOnCheckedChangeListener(checkboxListener);
        cbFive.setOnCheckedChangeListener(checkboxListener);
        cbMoreThanFive.setOnCheckedChangeListener(checkboxListener);


        btnBack8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(QuestionPage5.this, QuestionPage4.class);
                startActivity(backIntent);
            }
        });


        btnContinue8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextIntent = new Intent(QuestionPage5.this, QuestionPage6.class);
                startActivity(nextIntent);
            }
        });
    }
}
