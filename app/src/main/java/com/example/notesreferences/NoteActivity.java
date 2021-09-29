package com.example.notesreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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
            Intent intent = new Intent();
            intent.putExtra(TITLE_KEY, title.getText().toString());
            intent.putExtra(DESCRIPTION_KEY, description.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        });

    }
}