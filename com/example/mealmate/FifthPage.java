package com.example.mealmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.animation.ValueAnimator;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FifthPage extends AppCompatActivity {

    private Button btnBack3, btnGotIt;
    private TextView tvPickMeals, tvGroceryShop, tvCook;
    private View line1, line2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifthpage);

        btnBack3 = findViewById(R.id.btnBack3);
        btnGotIt = findViewById(R.id.btnGotIt);
        tvPickMeals = findViewById(R.id.tvPickMeals);
        tvGroceryShop = findViewById(R.id.tvGroceryShop);
        tvCook = findViewById(R.id.tvCook);
        line1 = findViewById(R.id.line1);
        line2 = findViewById(R.id.line2);

        fadeInTextViews();

        btnBack3.setOnClickListener(v -> {
            Intent intent = new Intent(FifthPage.this, FourthPage.class);
            startActivity(intent);
        });

        btnGotIt.setOnClickListener(v -> {
            Intent intent = new Intent(FifthPage.this, QuestionPage.class);
            startActivity(intent);
        });
    }

    private void fadeInTextViews() {
        AlphaAnimation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(900);
        tvPickMeals.postDelayed(() -> {
            tvPickMeals.startAnimation(fadeIn);
            tvPickMeals.setVisibility(View.VISIBLE);
            animateLine(line1);
        }, 500);

        tvGroceryShop.postDelayed(() -> {
            tvGroceryShop.startAnimation(fadeIn);
            tvGroceryShop.setVisibility(View.VISIBLE);
            animateLine(line2);
        }, 1500);


        tvCook.postDelayed(() -> {
            tvCook.startAnimation(fadeIn);
            tvCook.setVisibility(View.VISIBLE);
        }, 2500);

    }

    private void animateLine(View line) {
        ValueAnimator animator = ValueAnimator.ofInt(0, 500);  // line width from 0 to 500
        animator.setDuration(1000);
        animator.addUpdateListener(valueAnimator -> {
            int width = (int) valueAnimator.getAnimatedValue();
            line.getLayoutParams().width = width;
            line.requestLayout();
        });
        animator.start();
    }
}
