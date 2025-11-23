package com.myapp.notely;import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Check if user is logged in
        if (currentUser != null) {
            // User is already signed in, go directly to MainActivity
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        } else {
            // No user is signed in, go to LoginActivity
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }

        // Finish SplashActivity so the user can't navigate back to it
        finish();
    }
}
