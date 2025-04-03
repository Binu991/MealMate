package com.example.mealmate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    private ImageView ivProfilePicture;
    private EditText etUsername, etAddress, etPhone;
    private TextView tvEmail;
    private ImageButton btnRemovePic;
    private Button btnSaveChanges;

    private Uri selectedImageUri;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        Glide.with(this).load(selectedImageUri).into(ivProfilePicture);
                        editor.putString("profile_image_uri", selectedImageUri.toString());
                        editor.apply();
                        Toast.makeText(this, "Profile picture updated!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Init Views
        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        etUsername = findViewById(R.id.etUsername);
        etAddress = findViewById(R.id.etAddress);
        etPhone = findViewById(R.id.etPhone);
        tvEmail = findViewById(R.id.tvEmail);
        btnRemovePic = findViewById(R.id.btnRemovePic);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);

        prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        editor = prefs.edit();

        // Load saved data
        String username = prefs.getString("username", "");
        String address = prefs.getString("address", "");
        String phone = prefs.getString("phone", "");
        String profileUri = prefs.getString("profile_image_uri", null);
        String firebaseEmail = FirebaseAuth.getInstance().getCurrentUser() != null ?
                FirebaseAuth.getInstance().getCurrentUser().getEmail() : "user@example.com";

        etUsername.setText(username);
        etAddress.setText(address);
        etPhone.setText(phone);
        tvEmail.setText(firebaseEmail);

        if (profileUri != null) {
            Glide.with(this).load(Uri.parse(profileUri)).into(ivProfilePicture);
        }

        // Image Picker
        ivProfilePicture.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        // Remove picture
        btnRemovePic.setOnClickListener(v -> {
            ivProfilePicture.setImageResource(R.drawable.ic_profile);
            editor.remove("profile_image_uri").apply();
            Toast.makeText(this, "Profile picture removed", Toast.LENGTH_SHORT).show();
        });

        // Save all
        btnSaveChanges.setOnClickListener(v -> {
            String newUsername = etUsername.getText().toString().trim();
            String newAddress = etAddress.getText().toString().trim();
            String newPhone = etPhone.getText().toString().trim();

            if (TextUtils.isEmpty(newUsername)) {
                Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            editor.putString("username", newUsername);
            editor.putString("address", newAddress);
            editor.putString("phone", newPhone);
            editor.apply();

            Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        });
    }
}
