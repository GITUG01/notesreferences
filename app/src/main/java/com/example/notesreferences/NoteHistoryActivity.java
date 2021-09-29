package com.example.notesreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.notesreferences.domain.NotesDatabase;

public class NoteHistoryActivity extends AppCompatActivity {

    public static TextView textView;
    ImageButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_history);

        textView = findViewById(R.id.history_tvv);
        btn = findViewById(R.id.back_btn);

        btn.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

    }
}