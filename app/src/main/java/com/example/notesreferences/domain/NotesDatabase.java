package com.example.notesreferences.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.notesreferences.NoteActivity;

public class NotesDatabase {

    String title;
    String description;

    public NotesDatabase() {
        Log.d("my", "insert in table");
    }

public void addToBD(Context context, String title, String description){
    BDHelper bdHelper = new BDHelper(context);
    ContentValues cv = new ContentValues();

    SQLiteDatabase bd = bdHelper.getWritableDatabase();

    cv.put(NoteActivity.TITLE_KEY, title);
    cv.put(NoteActivity.DESCRIPTION_KEY, description);

    bd.insert("notesBDD", null, cv);

}








    static class BDHelper extends SQLiteOpenHelper {

        public BDHelper(@Nullable Context context) {
            super(context, "myBD", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table notesBDD ("
                    + "id integer primary key autoincrement,"
                    + "description text,"
                    + "title text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    }
}
