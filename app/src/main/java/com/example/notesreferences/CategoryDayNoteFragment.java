package com.example.notesreferences;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notesreferences.categories.domain.CategoryEmpty;
import com.example.notesreferences.domain.NoteEntity;
import com.example.notesreferences.domain.NoteRepo;
import com.example.notesreferences.impl.NoteRepoImpl;
import com.example.notesreferences.ui.NotesAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryDayNoteFragment extends Fragment {


    RecyclerView recyclerView;

    public NoteRepo noteRepo = new NoteRepoImpl();
    private NotesAdapter adapter = new NotesAdapter();

    private String title;
    private String description;

    public final static String DAY_NOTE_TABLE_NAME = "DayNoteTable";
    public static BDHelper bdHelper;
    SQLiteDatabase bd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_day_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_day_note);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setData(noteRepo.notes());

        super.onViewCreated(view, savedInstanceState);


        getParentFragmentManager().setFragmentResultListener(MainActivity.DATA_DAY_NOTE, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String title = result.getString("title");
                String description = result.getString("description");

                noteRepo.addNote(new NoteEntity(title, description));
                DataBase(title, description);
//                writeDataBase();
            }
        });
    }

//    public void createNoteDay(String title, String description) {
//        noteRepo.addNote(new NoteEntity(title, description));
//    }

    static class BDHelper extends SQLiteOpenHelper {

        public BDHelper(@Nullable Context context) {
            super(context, DAY_NOTE_TABLE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table " + DAY_NOTE_TABLE_NAME + " ("
                    + "id integer primary key autoincrement,"
                    + "description text,"
                    + "title text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }

    public void DataBase(String title, String description) {
        bdHelper = new BDHelper(getContext());
        ContentValues cv = new ContentValues();

        bd = bdHelper.getWritableDatabase();

        cv.put(MainActivity.TITLE_KEY, title);
        cv.put(MainActivity.DESCRIPTION_KEY, description);

        bd.insert(DAY_NOTE_TABLE_NAME, null, cv);
        Log.d("@@@ mylogs", "Create note. Title: " + title + " Description: " + description);
    }

    public void writeDataBase() {
        Cursor c = bd.query(DAY_NOTE_TABLE_NAME, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            int columnID = c.getColumnIndex("id");
            int columnTitle = c.getColumnIndex(MainActivity.TITLE_KEY);
            int columnDescription = c.getColumnIndex(MainActivity.DESCRIPTION_KEY);

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
}