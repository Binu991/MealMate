package com.example.mealmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mealmate.helper.UserSessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUp extends AppCompatActivity {

    private EditText editEmail, editPassword, editConfirmPassword;
    private Button btnSignUp, btnLogin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editConfirmPassword = findViewById(R.id.editConfirmPassword);
        btnSignUp = findViewById(R.id.Signupbtn);
        btnLogin = findViewById(R.id.LoginBtn);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                String confirmPassword = editConfirmPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    Toast.makeText(SignUp.this, "Password too short. Must be at least 6 characters.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.matches(".*[A-Za-z].*") || !password.matches(".*[0-9].*")) {
                    Toast.makeText(SignUp.this, "Password must contain both letters and numbers.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (confirmPassword.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please confirm your password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    // ✅ Save user email to SharedPreferences
                                    UserSessionManager sessionManager = new UserSessionManager(SignUp.this);
                                    sessionManager.saveUserEmail(email);

                                    if (firebaseAuth.getCurrentUser() != null) {
                                        firebaseAuth.getCurrentUser().sendEmailVerification()
                                                .addOnCompleteListener(SignUp.this, new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> emailTask) {
                                                        if (emailTask.isSuccessful()) {
                                                            Toast.makeText(SignUp.this,
                                                                    "Verification email sent. Please check your email and click the link to verify.",
                                                                    Toast.LENGTH_LONG).show();
                                                            startActivity(new Intent(SignUp.this, LogIn.class));
                                                            finish();
                                                        } else {
                                                            Toast.makeText(SignUp.this,
                                                                    "Failed to send verification email. Try again later.",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                } else {
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthUserCollisionException e) {
                                        Toast.makeText(SignUp.this, "Email already in use.", Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        Toast.makeText(SignUp.this, "Signup failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });

        btnLogin.setOnClickListener(view -> {
            startActivity(new Intent(SignUp.this, LogIn.class));
            finish();
        });
    }
}
