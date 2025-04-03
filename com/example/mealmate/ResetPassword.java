package com.example.mealmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPassword extends AppCompatActivity {

    private EditText editNewPassword, editConfirmPassword;
    private Button btnSaveNewPassword;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        firebaseAuth = FirebaseAuth.getInstance();

        editNewPassword = findViewById(R.id.editNewPassword);
        editConfirmPassword = findViewById(R.id.editConfirmPassword);
        btnSaveNewPassword = findViewById(R.id.btnSaveNewPassword);

        btnSaveNewPassword.setOnClickListener(view -> {
            String newPassword = editNewPassword.getText().toString().trim();
            String confirmPassword = editConfirmPassword.getText().toString().trim();

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(ResetPassword.this, "Please fill both password fields", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(ResetPassword.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }
            if (newPassword.length() < 6) {
                Toast.makeText(ResetPassword.this, "Password too short. Must be at least 6 characters.", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                user.updatePassword(newPassword)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPassword.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ResetPassword.this, LogIn.class));
                                finish();
                            } else {
                                Toast.makeText(ResetPassword.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
