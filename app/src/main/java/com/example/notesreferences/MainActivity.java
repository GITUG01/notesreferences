package com.example.notesreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesreferences.categories.domain.CategoryEmpty;
import com.example.notesreferences.categories.ui.CategoryAdapter;
import com.example.notesreferences.domain.NoteEntity;
import com.example.notesreferences.domain.NoteRepo;
import com.example.notesreferences.domain.NotesDatabase;
import com.example.notesreferences.impl.NoteRepoImpl;
import com.example.notesreferences.ui.NotesAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerView recyclerItem;

    NoteRepo noteRepo = new NoteRepoImpl();
    private NotesAdapter adapter = new NotesAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setData(noteRepo.notes());

        List<CategoryEmpty> categories = new ArrayList<>();
        categories.add(new CategoryEmpty(1, "Заметки на день"));
        categories.add(new CategoryEmpty(2, "Долгосрочные"));
        categories.add(new CategoryEmpty(3, "Временные"));
        categories.add(new CategoryEmpty(3, "Еще что-то"));
        categories.add(new CategoryEmpty(3, "И еще что-то"));

        setCategoryAdapter(categories);


        noteRepo.addNote(new NoteEntity("Note 1", "Some text"));
        noteRepo.addNote(new NoteEntity("Note 2", "Some пп ппп  п пп п пп пщиопьишорт ропешо иешиотештепш ие икг икщг р пп е и рн тр нт  нтнт г нри  епи   епиепипеиtext"));
        noteRepo.addNote(new NoteEntity("Note 3", "Some text"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_note:
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.hg2, new CreateNoteFragment())
                        .commit();
                Toast.makeText(this, "Add note", Toast.LENGTH_SHORT).show();
                break;
            case R.id.del_menu:
                Toast.makeText(this, "Remove menu", Toast.LENGTH_SHORT).show();
                break;
            case R.id.read_notes:
                Intent intent02 = new Intent(this, NoteHistoryActivity.class);
                startActivity(intent02);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        NotesDatabase notesDatabase = new NotesDatabase();
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String title = data.getStringExtra(NoteActivity.TITLE_KEY);
            String description = data.getStringExtra(NoteActivity.DESCRIPTION_KEY);
            notesDatabase.addToBD(this, title, description);
            noteRepo.addNote(new NoteEntity(title, description));
        }
    }

    private void setCategoryAdapter(List<CategoryEmpty> categoryEmptyList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        recyclerItem = findViewById(R.id.item_recycler);
        recyclerItem.setLayoutManager(layoutManager);

        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categoryEmptyList);

        recyclerItem.setAdapter(categoryAdapter);
    }
}