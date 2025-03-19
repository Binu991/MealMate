package com.example.mealmate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LogIn extends AppCompatActivity {

    private EditText txt_email, txt_password;
    private Button loginBtn, forgotPassword, signupbtn;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize FirebaseAuth instance
        firebaseAuth = FirebaseAuth.getInstance();

        // Get views
        txt_email = findViewById(R.id.txt_email);
        txt_password = findViewById(R.id.txt_password);
        loginBtn = findViewById(R.id.LoginBtn);
        forgotPassword = findViewById(R.id.forgotPassword);
        signupbtn = findViewById(R.id.Signupbtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txt_email.getText().toString().trim();
                String password = txt_password.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(LogIn.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(LogIn.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty()) {
                    Toast.makeText(LogIn.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LogIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    Toast.makeText(LogIn.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LogIn.this, MainActivity.class));
                                    finish();
                                } else {
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthInvalidUserException e) {
                                        Toast.makeText(LogIn.this, "Login Failed", Toast.LENGTH_SHORT).show();

                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        Toast.makeText(LogIn.this, "Login Failed", Toast.LENGTH_SHORT).show();

                                    } catch (Exception e) {
                                        Toast.makeText(LogIn.this, "Login Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogIn.this, ForgotPassword.class));
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogIn.this, SignUp.class));
                finish();
            }
        });
    }
}
