package com.example.notesreferences;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesreferences.categories.domain.CategoryEmpty;
import com.example.notesreferences.categories.ui.CategoryAdapter;
import com.example.notesreferences.categories.ui.CategoryViewHolder;
import com.example.notesreferences.domain.NoteEntity;
import com.example.notesreferences.domain.NoteRepo;
import com.example.notesreferences.impl.NoteRepoImpl;
import com.example.notesreferences.ui.NotesAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements CreateNoteFragment.startMainFragment, CreateNoteFragment.sendData, CategoryViewHolder.OnCategoryListener, CreateNoteFragment.sendDataToDayNote, CreateNoteFragment.closeCreateNote {
    public final static String DATA_TEMPORARY = "dadaFromTemporary";
    public final static String DATA_TEMPORARY_TO_MAIN = "dadaFromTemporaryToMain";
    public final static String DATA_LONG_TERM = "dataFromLongTerm";
    public final static String DATA_LONG_TERM_TO_MAIN = "dataFromLongTermToMain";
    public final static String DATA_DAY_NOTE = "dataFromDayNote";
    public final static String DATA_DAY_NOTE_TO_MAIN = "dataFromDayNoteToMain";
    public final static String DATA_TO_MAIN = "data";

    public final static String TABLE_NAME = "mytable";
    public List<CategoryEmpty> categories = new ArrayList<>();
    RecyclerView recyclerItem;
    BottomNavigationView navigationView;
    NoteRepo noteRepo = new NoteRepoImpl();
    SQLiteDatabase bd;
    Map<Integer, Fragment> fragmentMap = new HashMap<>();
    private Toolbar toolbar;
    private final NotesAdapter adapter = new NotesAdapter();
    private BDHelper bdHelper;
    private final Map<Integer, Fragment> fragments = new HashMap<>();
    private final List<Integer> notesList = new ArrayList<>();
    private final MainActivityFragment mainActivityFragment = new MainActivityFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        fragmentMap.put(0, mainActivityFragment);   //test variant

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, Objects.requireNonNull(fragmentMap.get(0)))
                .commit();


        navigationView = findViewById(R.id.navigation_menu);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.fragment_container, Objects.requireNonNull(fragmentMap.get(0)))
                                .addToBackStack(null)
                                .commit();
//                        setContentView(R.layout.activity_main);
                }
                return false;
            }
        });

//        recyclerView = findViewById(R.id.recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//        adapter.setData(noteRepo.notes());

        categories.add(new CategoryEmpty(1, "Day note"));
        categories.add(new CategoryEmpty(2, "Long-term"));
        categories.add(new CategoryEmpty(3, "Temporary"));
        categories.add(new CategoryEmpty(3, "Product list"));
        categories.add(new CategoryEmpty(3, "And something else"));

        setCategoryAdapter(categories);

        fragments.put(0, new CategoryDayNoteFragment());
        fragments.put(1, new CategoryLongTermFragment());
        fragments.put(2, new CategoryProductListFragment());
        fragments.put(3, new CategoryTemporaryFragment());

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
        notesList.add(noteRepo.addNote(new NoteEntity(title, description)));
        noteRepo.addNote(new NoteEntity(title, description));
        DataBase(title, description);
        adapter.setData(noteRepo.notes());
    }

    @Override
    public void sendDataToDayNote(String title, String description) {

    }

    @Override
    public void onCategoryClick(int position) {
        categories.get(position);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, Objects.requireNonNull(fragments.get(position)))
                .addToBackStack(null)
                .commit();
    }


    public void DataBase(String title, String description) {
        bdHelper = new BDHelper(this);
        ContentValues cv = new ContentValues();

        bd = bdHelper.getWritableDatabase();

        cv.put(NoteActivity.TITLE_KEY, title);
        cv.put(NoteActivity.DESCRIPTION_KEY, description);

        bd.insert(TABLE_NAME, null, cv);
        Log.d("@@@ mylogs", "Create note. Title: " + title + " Description: " + description);
    }

    public void writeDataBase() {
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
    public void closeCreateNote() {
        getFragmentManager().popBackStack();

    }

    @Override
    public void startMainFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, Objects.requireNonNull(fragmentMap.get(0)))
                .commit();
    }

//    @Override
//    public void sendDataToMainFragment(String title, String description) {
//        Bundle result = new Bundle();
//        result.putString("title", title);
//        result.putString("description", description));
//        getParentFragmentManager().setFragmentResult(requestKey, result);
//    }


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


