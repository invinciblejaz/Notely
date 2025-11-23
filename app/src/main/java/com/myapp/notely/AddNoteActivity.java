package com.myapp.notely;
import android.util.Log;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddNoteActivity extends AppCompatActivity {

    Button btnSave;
    EditText etTitle, etContent;
    FirebaseAuth auth;

    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        btnSave = findViewById(R.id.btnSave);
        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNotes();
            }
        });

    }

    private void saveNotes() {
        String title = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(AddNoteActivity.this, "Write something before saving!", Toast.LENGTH_SHORT).show();
            return;  // stay on this screen
        }

        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            Toast.makeText(AddNoteActivity.this, "User not logged in!", Toast.LENGTH_SHORT).show();
            // ideally redirect to login instead of just finish()
            // startActivity(new Intent(AddNoteActivity.this, LoginActivity.class));
            finish();
            return;
        }

        String userid = user.getUid();

        Map<String, Object> note = new HashMap<>();
        note.put("title", title);
        note.put("content", content);
        note.put("timestamp", FieldValue.serverTimestamp());
        note.put("pinned",false);

        db.collection("users")
                .document(userid)
                .collection("notes")
                .add(note)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(AddNoteActivity.this, "Note added successfully!", Toast.LENGTH_SHORT).show();

                    // NOW go back to main screen
                    Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddNoteActivity.this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    // stay here so user can retry
                });
    }
}