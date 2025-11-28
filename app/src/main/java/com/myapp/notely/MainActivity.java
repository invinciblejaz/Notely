package com.myapp.notely;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    ExtendedFloatingActionButton btnAddNote;
    RecyclerView rvNotes;
    Button btnLogout;

    ArrayList<Note> noteList;
    NotesAdapter adapter;

    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI
        btnAddNote = findViewById(R.id.fabAddNote);
        rvNotes = findViewById(R.id.rvNotes);
        btnLogout = findViewById(R.id.btnLogout);


        // Firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            Log.d(TAG, "No logged in user");
            return;
        }

        // FAB → Add note screen (NO finish() here)
        btnAddNote.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddNoteActivity.class));
        });

        // RecyclerView setup
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvNotes.setLayoutManager(layoutManager);

        noteList = new ArrayList<>();
        adapter = new NotesAdapter(this, noteList, note -> {
            Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
            intent.putExtra("noteId", note.getId());
            intent.putExtra("title", note.getTitle());
            intent.putExtra("content", note.getContent());
            startActivity(intent);
        });
        rvNotes.setAdapter(adapter);


        // Load notes from Firestore live
        loadNotes(user.getUid());
    }

    private void loadNotes(String userId) {
        db.collection("users")
                .document(userId)
                .collection("notes")
                .orderBy("pinned", Query.Direction.DESCENDING)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.e(TAG, "Firestore load error", error);
                        return;
                    }

                    noteList.clear();
                    if (value != null) {
                        value.getDocuments().forEach(doc -> {
                            Note note = doc.toObject(Note.class);
                            note.setId(doc.getId());
                            noteList.add(note);
                        });
                    }

                    adapter.notifyDataSetChanged();
                });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
