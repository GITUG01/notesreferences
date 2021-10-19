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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesreferences.categories.domain.CategoryEntity;
import com.example.notesreferences.categories.ui.CategoryAdapter;
import com.example.notesreferences.categories.ui.CategoryViewHolder;
import com.example.notesreferences.categoryFragments.CategoryDayNoteFragment;
import com.example.notesreferences.categoryFragments.CategoryLongTermFragment;
import com.example.notesreferences.categoryFragments.CategoryProductListFragment;
import com.example.notesreferences.categoryFragments.CategoryTemporaryFragment;
import com.example.notesreferences.domain.NoteEntity;
import com.example.notesreferences.domain.NoteRepo;
import com.example.notesreferences.impl.NoteRepoImpl;
import com.example.notesreferences.ui.NotesAdapter;
import com.example.notesreferences.ui.SelectListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnBackButton, SelectListener, CategoryProductListFragment.hideKeyboard, CreateNoteFragment.hideKeyboardCreateNote, CreateNoteFragment.startTemporaryFragment, CreateNoteFragment.startCategoryDayNoteFragment, CreateNoteFragment.startCategoryLongTermFragment, CategoryViewHolder.OnCategoryListener {
    public final static String DATA_TEMPORARY = "dadaFromTemporary";
    public final static String DATA_TEMPORARY_TO_MAIN = "dadaFromTemporaryToMain";
    public final static String DATA_LONG_TERM = "dataFromLongTerm";
    public final static String DATA_LONG_TERM_TO_MAIN = "dataFromLongTermToMain";
    public final static String DATA_DAY_NOTE = "dataFromDayNote";
    public final static String DATA_DAY_NOTE_TO_MAIN = "dataFromDayNoteToMain";
    public final static String DATA_TO_MAIN = "data";
    public static final String TITLE_KEY = "title";
    public final static String DESCRIPTION_KEY = "description";
    public final static String DATA_T0_EDIT = "dataToEdit";

    public final static String TABLE_NAME = "mytable";
    private final NotesAdapter adapter = new NotesAdapter(this);
    private final Map<Integer, Fragment> fragments = new HashMap<>();
    private final List<Integer> notesList = new ArrayList<>();

    private final MainActivityFragment mainActivityFragment = new MainActivityFragment();
    private final CategoryDayNoteFragment categoryDayNoteFragment = new CategoryDayNoteFragment();
    private final CategoryLongTermFragment categoryLongTermFragment = new CategoryLongTermFragment();
    private final CategoryTemporaryFragment categoryTemporaryFragment = new CategoryTemporaryFragment();
    private final CategoryProductListFragment categoryProductListFragment = new CategoryProductListFragment();

    public List<CategoryEntity> categories = new ArrayList<>();
    RecyclerView recyclerItem;
    BottomNavigationView navigationView;
    NoteRepo noteRepo = new NoteRepoImpl();
    SQLiteDatabase bd;
    Map<Integer, Fragment> fragmentMap = new HashMap<>();
    private Toolbar toolbar;
    private BDHelper bdHelper;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        fragmentMap.put(0, mainActivityFragment);
        fragmentMap.put(1, categoryDayNoteFragment);
        fragmentMap.put(2, categoryLongTermFragment);
        fragmentMap.put(3, categoryTemporaryFragment);
        fragmentMap.put(4, categoryProductListFragment);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, Objects.requireNonNull(fragmentMap.get(0)))
                .commit();


        navigationView = findViewById(R.id.navigation_menu);
        navigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(R.id.fragment_container, 0);
            }
            return false;
        });

        categories.add(new CategoryEntity(1, "Day note"));
        categories.add(new CategoryEntity(2, "Long-term"));
        categories.add(new CategoryEntity(3, "Temporary"));
        categories.add(new CategoryEntity(3, "Product list"));
        categories.add(new CategoryEntity(3, "And something else"));

        setCategoryAdapter(categories);

        fragments.put(0, fragmentMap.get(1));
        fragments.put(1, fragmentMap.get(2));
        fragments.put(2, fragmentMap.get(3));
        fragments.put(3, fragmentMap.get(4));

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragment_container, new CreateNoteFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case 0:
                Toast.makeText(this, "case 0", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "case 1", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment instanceof OnBackButton) {
                    new AlertDialog.Builder(this)
                .setTitle("title")
                .setMessage("Confirm exciting app")
                .setPositiveButton("Confirm", ((dialogInterface, i) -> {
                    finish();
                }))
                .setNegativeButton("No", ((dialogInterface, i) -> {
                }))
                .show();
        } else {
            super.onBackPressed();
        }
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
//                getSupportFragmentManager()
//                        .beginTransaction()
//                        .add(R.id.fragment_container, new CreateNoteFragment())
//                        .addToBackStack(null)
//                        .commit();
//                Toast.makeText(this, "Add note", Toast.LENGTH_SHORT).show();
                break;
            case R.id.del_menu:
                Toast.makeText(this, "Remove menu", Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setCategoryAdapter(List<CategoryEntity> categoryEmptyList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);

        recyclerItem = findViewById(R.id.item_recycler);
        recyclerItem.setLayoutManager(layoutManager);

        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categoryEmptyList, this);

        recyclerItem.setAdapter(categoryAdapter);
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

        cv.put(TITLE_KEY, title);
        cv.put(DESCRIPTION_KEY, description);

        bd.insert(TABLE_NAME, null, cv);
        Log.d("@@@ mylogs", "Create note. Title: " + title + " Description: " + description);
    }

    public void writeDataBase() {
        Cursor c = bd.query(TABLE_NAME, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            int columnID = c.getColumnIndex("id");
            int columnTitle = c.getColumnIndex(TITLE_KEY);
            int columnDescription = c.getColumnIndex(DESCRIPTION_KEY);

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
    public void startCategoryDayNoteFragment() {
        replaceFragment(R.id.fragment_container, 1);
    }

    @Override
    public void startCategoryLongTermFragment() {
        replaceFragment(R.id.fragment_container, 2);
    }

    private void replaceFragment(@IdRes int containerViewId, int fragmentContainerPosition) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerViewId, Objects.requireNonNull(fragmentMap.get(fragmentContainerPosition)))
                .commit();
    }

    @Override
    public void startTemporaryFragment() {
        replaceFragment(R.id.fragment_container, 3);
    }


    @Override
    public void hideKeyboardCreateNote() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onItemClicked(NoteEntity noteEntity) {
        Toast.makeText(this, noteEntity.getTitle(), Toast.LENGTH_SHORT).show();
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


