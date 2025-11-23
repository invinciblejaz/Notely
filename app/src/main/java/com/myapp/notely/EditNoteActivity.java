package com.myapp.notely;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditNoteActivity extends AppCompatActivity {

    EditText etTitle, etContent;
    Button btnUpdate, btnDelete;

    FirebaseAuth auth;
    FirebaseFirestore db;

    String noteId;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if (auth.getCurrentUser() == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        userId = auth.getCurrentUser().getUid();

        noteId = getIntent().getStringExtra("noteId");
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");

        if (noteId == null) {
            Toast.makeText(this, "Invalid note", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        etTitle.setText(title);
        etContent.setText(content);

        btnUpdate.setOnClickListener(v -> updateNote());
        btnDelete.setOnClickListener(v -> deleteNote());
    }

    private void updateNote() {
        String newTitle = etTitle.getText().toString().trim();
        String newContent = etContent.getText().toString().trim();

        if (newTitle.isEmpty() || newContent.isEmpty()) {
            Toast.makeText(this, "Title and content cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("title", newTitle);
        updateMap.put("content", newContent);
        updateMap.put("timestamp", FieldValue.serverTimestamp()); // ensures updated notes appear first

        db.collection("users")
                .document(userId)
                .collection("notes")
                .document(noteId)
                .update(updateMap)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Update failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    private void deleteNote() {
        db.collection("users")
                .document(userId)
                .collection("notes")
                .document(noteId)
                .delete()
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Delete failed: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }
}
