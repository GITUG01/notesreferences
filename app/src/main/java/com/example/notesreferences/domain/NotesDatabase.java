package com.example.notesreferences.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Layout;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.notesreferences.MainActivity;
import com.example.notesreferences.NoteActivity;

public class NotesDatabase {
    public final static String TABLE_NAME = "mytable";
    String title;
    String description;

    private BDHelper bdHelper;
    SQLiteDatabase bd;

    public NotesDatabase() {
        Log.d("my", "insert in table");
    }

    public void addToBD(Context context, String title, String description) {
        bdHelper = new BDHelper(context);
        ContentValues cv = new ContentValues();

        bd = bdHelper.getWritableDatabase();

        cv.put(NoteActivity.TITLE_KEY, title);
        cv.put(NoteActivity.DESCRIPTION_KEY, description);

        bd.insert(TABLE_NAME, null, cv);


    }

//    public void getData() {
//        Cursor c = bd.query(TABLE_NAME, null, null, null, null, null, null);
//
//        if (c.moveToFirst()) {
//            int columnID = c.getColumnIndex("id");
//            int columnTitle = c.getColumnIndex(NoteActivity.TITLE_KEY);
//            int columnDescription = c.getColumnIndex(NoteActivity.DESCRIPTION_KEY);
//
//            do {
//                textView.append("Note № " + c.getInt(columnID) +
//                        " Title: " + c.getString(columnTitle) +
//                        " Description: +" + c.getString(columnDescription));
//            } while (c.moveToNext());
//
//        } else {
//            textView.append("That's all");
//
//        }
//        c.close();
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
