package com.example.notesreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.notesreferences.domain.NoteEntity;
import com.example.notesreferences.domain.NoteRepo;
import com.example.notesreferences.domain.NotesDatabase;
import com.example.notesreferences.impl.NoteRepoImpl;
import com.example.notesreferences.ui.NotesAdapter;

public class NoteHistoryActivity extends AppCompatActivity {

    public static TextView textView;
    ImageButton btn;
    private NotesAdapter adapter = new NotesAdapter();
    NoteRepo noteRepo1 = new NoteRepoImpl();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_history);

        textView = findViewById(R.id.history_tvv);
        btn = findViewById(R.id.back_btn);
        recyclerView = findViewById(R.id.recycler02);

        recyclerView = findViewById(R.id.recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
        adapter.setData(noteRepo1.notes());

        noteRepo1.addNote(new NoteEntity("Title1", "description"));

        btn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

    }
}