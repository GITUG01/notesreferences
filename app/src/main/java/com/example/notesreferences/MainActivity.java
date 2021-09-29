package com.example.notesreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.notesreferences.domain.NoteEntity;
import com.example.notesreferences.domain.NoteRepo;
import com.example.notesreferences.impl.NoteRepoImpl;
import com.example.notesreferences.ui.NotesAdapter;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    RecyclerView recyclerView;

    NoteRepo noteRepo = new NoteRepoImpl();
    private NotesAdapter adapter = new NotesAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setData(noteRepo.notes());

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        noteRepo.addNote(new NoteEntity("Note 1", "Some text"));
        noteRepo.addNote(new NoteEntity("Note 2", "Some пп ппп  п пп п пп пщиопьишорт ропешо иешиотештепш ие икг икщг р пп е и рн тр нт  нтнт г нри  епи   епиепипеиtext"));
        noteRepo.addNote(new NoteEntity("Note 3", "Some text"));
        noteRepo.addNote(new NoteEntity("Note 4", "Some text"));
        noteRepo.addNote(new NoteEntity("Note 4", "Some text"));
        noteRepo.addNote(new NoteEntity("Note 4", "Some text"));
        noteRepo.addNote(new NoteEntity("Note 4", "Some text"));
        noteRepo.addNote(new NoteEntity("Note 4", "Some text"));
        noteRepo.addNote(new NoteEntity("Note 4", "Some text"));
        noteRepo.addNote(new NoteEntity("Note 4", "Some text"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.add_note:
                Toast.makeText(this, "Add some note", Toast.LENGTH_SHORT).show();
                break;
            case R.id.del_menu:
                Toast.makeText(this, "Remove some menu", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}