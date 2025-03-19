package com.example.mealmate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class QuestionPage4 extends Activity {

    private Button btnBack7, btnContinue7;
    private CheckBox cbSunday, cbMonday, cbTuesday, cbWednesday, cbThursday, cbFriday, cbSaturday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionpage4);

        btnBack7 = findViewById(R.id.btnBack7);
        btnContinue7 = findViewById(R.id.btnContinue7);

        cbSunday = findViewById(R.id.cbSunday);
        cbMonday = findViewById(R.id.cbMonday);
        cbTuesday = findViewById(R.id.cbTuesday);
        cbWednesday = findViewById(R.id.cbWednesday);
        cbThursday = findViewById(R.id.cbThursday);
        cbFriday = findViewById(R.id.cbFriday);
        cbSaturday = findViewById(R.id.cbSaturday);

        CompoundButton.OnCheckedChangeListener checkboxListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setTextColor(isChecked ? Color.GRAY : Color.BLACK);
            }
        };
        cbSunday.setOnCheckedChangeListener(checkboxListener);
        cbMonday.setOnCheckedChangeListener(checkboxListener);
        cbTuesday.setOnCheckedChangeListener(checkboxListener);
        cbWednesday.setOnCheckedChangeListener(checkboxListener);
        cbThursday.setOnCheckedChangeListener(checkboxListener);
        cbFriday.setOnCheckedChangeListener(checkboxListener);
        cbSaturday.setOnCheckedChangeListener(checkboxListener);

        btnBack7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(QuestionPage4.this, QuestionPage3.class);
                startActivity(backIntent);
            }
        });

        btnContinue7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextIntent = new Intent(QuestionPage4.this, QuestionPage5.class);
                startActivity(nextIntent);
            }
        });
    }
}
