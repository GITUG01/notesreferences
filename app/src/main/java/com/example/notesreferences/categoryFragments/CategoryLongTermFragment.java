package com.example.notesreferences.categoryFragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesreferences.EditNoteFragment;
import com.example.notesreferences.MainActivity;
import com.example.notesreferences.R;
import com.example.notesreferences.domain.NoteEntity;
import com.example.notesreferences.domain.NoteRepo;
import com.example.notesreferences.impl.NoteRepoImpl;
import com.example.notesreferences.ui.NotesAdapter;
import com.example.notesreferences.ui.SelectListener;

public class CategoryLongTermFragment extends Fragment implements SelectListener {

    public final static String LONG_TERM_TABLE_NAME = "LongTermTable";
    public static BDHelper bdHelper;
    public NoteRepo noteRepo = new NoteRepoImpl();
    RecyclerView recyclerView;
    SQLiteDatabase bd;
    private NotesAdapter adapter = new NotesAdapter(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readDataBase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category_long_term, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        recyclerView = view.findViewById(R.id.recycler_long_term);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setData(noteRepo.notes());


        super.onViewCreated(view, savedInstanceState);


        getParentFragmentManager().setFragmentResultListener(MainActivity.DATA_LONG_TERM, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String title = result.getString("title");
                String description = result.getString("description");

                noteRepo.addNote(new NoteEntity(title, description));

                DataBase(title, description);
                writeDataBase();
            }
        });
    }

    public void DataBase(String title, String description) {
        bdHelper = new BDHelper(getContext());
        ContentValues cv = new ContentValues();

        bd = bdHelper.getWritableDatabase();

        cv.put(MainActivity.TITLE_KEY, title);
        cv.put(MainActivity.DESCRIPTION_KEY, description);

        bd.insert(LONG_TERM_TABLE_NAME, null, cv);
        Log.d("@@@ mylogs", "Create note. Title: " + title + " Description: " + description);
    }

    public void readDataBase() {
        bdHelper = new BDHelper(getContext());
        bd = bdHelper.getReadableDatabase();

        Cursor c = bd.query(LONG_TERM_TABLE_NAME, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            int columnID = c.getColumnIndex("id");
            int columnTitle = c.getColumnIndex(MainActivity.TITLE_KEY);
            int columnDescription = c.getColumnIndex(MainActivity.DESCRIPTION_KEY);

            do {
                noteRepo.addNote(new NoteEntity(c.getString(columnTitle), c.getString(columnDescription)));
            } while (c.moveToNext());

        } else {
            Log.d("@@@ mylogs", "That's all");
        }
        c.close();
    }


//    int clearCount = db.delete("mytable", null, null);
//      Log.d(LOG_TAG, "deleted rows count = " + clearCount);


    public void writeDataBase() {
        Cursor c = bd.query(LONG_TERM_TABLE_NAME, null, null, null, null, null, null);

        if (c.moveToFirst()) {
            int columnID = c.getColumnIndex("id");
            int columnTitle = c.getColumnIndex(MainActivity.TITLE_KEY);
            int columnDescription = c.getColumnIndex(MainActivity.DESCRIPTION_KEY);

            do {
                Log.d("@@@ mylogs", "Table: " + LONG_TERM_TABLE_NAME + "Note № " + c.getInt(columnID) +
                        " Title: " + c.getString(columnTitle) +
                        " Description: " + c.getString(columnDescription));
            } while (c.moveToNext());

        } else {
            Log.d("@@@ mylogs", "That's all");
        }
        c.close();
    }

    @Override
    public void onItemClicked(NoteEntity noteEntity) {
        Toast.makeText(getContext(), noteEntity.getTitle(), Toast.LENGTH_SHORT).show();
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new EditNoteFragment())
                .addToBackStack(null)
                .commit();

        Bundle result = new Bundle();
        result.putString("title", noteEntity.getTitle());
        result.putString("description", noteEntity.getDetale());
//        result.putSerializable("gg", (Serializable) noteEntity);
        getParentFragmentManager().setFragmentResult(MainActivity.DATA_T0_EDIT, result);
    }


    static class BDHelper extends SQLiteOpenHelper {

        public BDHelper(@Nullable Context context) {
            super(context, LONG_TERM_TABLE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table " + LONG_TERM_TABLE_NAME + " ("
                    + "id integer primary key autoincrement,"
                    + "description text,"
                    + "title text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}