package com.example.notesreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesreferences.categories.domain.CategoryEmpty;
import com.example.notesreferences.categories.ui.CategoryAdapter;
import com.example.notesreferences.categories.ui.CategoryViewHolder;
import com.example.notesreferences.domain.NoteEntity;
import com.example.notesreferences.domain.NoteRepo;
import com.example.notesreferences.domain.NotesDatabase;
import com.example.notesreferences.impl.NoteRepoImpl;
import com.example.notesreferences.ui.NotesAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CreateNoteFragment.sendData, CategoryViewHolder.OnCategoryListener {
    private Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerView recyclerItem;

    NoteRepo noteRepo = new NoteRepoImpl();
    private NotesAdapter adapter = new NotesAdapter();
    public final static String TABLE_NAME = "mytable";
    private BDHelper bdHelper;
    SQLiteDatabase bd;
    public List<CategoryEmpty> categories = new ArrayList<>();


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


        categories.add(new CategoryEmpty(1, "Day note"));
        categories.add(new CategoryEmpty(2, "Long-term"));
        categories.add(new CategoryEmpty(3, "Temporary"));
        categories.add(new CategoryEmpty(3, "Product list"));
        categories.add(new CategoryEmpty(3, "And something else"));

        setCategoryAdapter(categories);

//        Cursor c = bd.query(TABLE_NAME, null, null, null, null, null, null);

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
                        .add(R.id.fragment_container, new CreateNoteFragment())
                        .addToBackStack(null)
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

    private void setCategoryAdapter(List<CategoryEmpty> categoryEmptyList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        recyclerItem = findViewById(R.id.item_recycler);
        recyclerItem.setLayoutManager(layoutManager);

        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categoryEmptyList, this);

        recyclerItem.setAdapter(categoryAdapter);
    }

    @Override
    public void sendData(String title, String description) {
        NotesDatabase notesDatabase = new NotesDatabase();
        noteRepo.addNote(new NoteEntity(title, description));
        DataBase(title, description);
        adapter.setData(noteRepo.notes());
//        }
    }

    public void DataBase(String title, String description) {
        bdHelper = new BDHelper(this);
        ContentValues cv = new ContentValues();

        bd = bdHelper.getWritableDatabase();

        cv.put(NoteActivity.TITLE_KEY, title);
        cv.put(NoteActivity.DESCRIPTION_KEY, description);

        bd.insert(TABLE_NAME, null, cv);
        Log.d("@@@ mylogs", "Create note. Title: " + title +" Description: " + description);
    }

public void writeDataBase(){
    Cursor c = bd.query(TABLE_NAME, null, null, null, null, null, null);

    if (c.moveToFirst()) {
        int columnID = c.getColumnIndex("id");
        int columnTitle = c.getColumnIndex(NoteActivity.TITLE_KEY);
        int columnDescription = c.getColumnIndex(NoteActivity.DESCRIPTION_KEY);

        do {
            Log.d("@@@ mylogs", "Note № " + c.getInt(columnID) +
                    " Title: " + c.getString(columnTitle) +
                    " Description: " + c.getString(columnDescription));
        } while (c.moveToNext());

    } else {
        Log.d("@@@ mylogs", "That's all");

    }
    c.close();
}




    @Override
    public void onCategoryClick(int position) {
        categories.get(position);
        Toast.makeText(this, "Open new fragment", Toast.LENGTH_SHORT).show();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new CreateNoteFragment())     //test variant
                .commit();
    }

    static class BDHelper extends SQLiteOpenHelper {

        public BDHelper(@Nullable Context context) {
            super(context, TABLE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table " + TABLE_NAME + " ("
                    + "id integer primary key autoincrement,"
                    + "description text,"
                    + "title text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
