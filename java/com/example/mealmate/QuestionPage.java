package com.example.mealmate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class QuestionPage extends Activity {

    private Button btnBack4, btnContinue4;
    private CheckBox cbDiet, cbEatingHabits, cbLoseWeight, cbGainWeight, cbSaveMoney, cbOrganized, cbPregnancy;
    private EditText etOtherReason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionpage1);

        // Initialize views
        btnBack4 = findViewById(R.id.btnBack4);
        btnContinue4 = findViewById(R.id.btnContinue4);
        cbDiet = findViewById(R.id.cbDiet);
        cbEatingHabits = findViewById(R.id.cbEatingHabits);
        cbLoseWeight = findViewById(R.id.cbLoseWeight);
        cbGainWeight = findViewById(R.id.cbGainWeight);
        cbSaveMoney = findViewById(R.id.cbSaveMoney);
        cbOrganized = findViewById(R.id.cbOrganized);
        cbPregnancy = findViewById(R.id.cbPregnancy);
        etOtherReason = findViewById(R.id.etOtherReason);

        btnBack4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuestionPage.this, FifthPage.class);
                startActivity(intent);
            }
        });

        btnContinue4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otherReason = etOtherReason.getText().toString().trim();


                Intent intent = new Intent(QuestionPage.this, QuestionPage2.class);
                intent.putExtra("otherReason", otherReason);
                startActivity(intent);
            }
        });

        CompoundButton.OnCheckedChangeListener checkboxListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.setTextColor(isChecked ? Color.GRAY : Color.BLACK);
            }
        };

        cbDiet.setOnCheckedChangeListener(checkboxListener);
        cbEatingHabits.setOnCheckedChangeListener(checkboxListener);
        cbLoseWeight.setOnCheckedChangeListener(checkboxListener);
        cbGainWeight.setOnCheckedChangeListener(checkboxListener);
        cbSaveMoney.setOnCheckedChangeListener(checkboxListener);
        cbOrganized.setOnCheckedChangeListener(checkboxListener);
        cbPregnancy.setOnCheckedChangeListener(checkboxListener);
    }
}
