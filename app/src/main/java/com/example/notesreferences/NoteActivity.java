package com.example.notesreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notesreferences.domain.NotesDatabase;

public class NoteActivity extends AppCompatActivity {

    EditText title;
    EditText description;
    Button btn;

    public static final String TITLE_KEY = "title";
    public final static String DESCRIPTION_KEY = "description";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        title = findViewById(R.id.title_et);
        description = findViewById(R.id.description_et);
        btn = findViewById(R.id.save_btn);


        btn.setOnClickListener(view -> {
            String titleStr = title.getText().toString();
            String descriptionStr = description.getText().toString();

            NotesDatabase notesDatabase = new NotesDatabase();
            notesDatabase.addToBD(this, titleStr, descriptionStr);
            Intent intent = new Intent();
            intent.putExtra(TITLE_KEY, titleStr);
            intent.putExtra(DESCRIPTION_KEY, descriptionStr);
            setResult(RESULT_OK, intent);
            finish();
        });

    }
}