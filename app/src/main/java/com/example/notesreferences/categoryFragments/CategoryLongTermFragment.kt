package com.example.notesreferences.categoryFragments

import com.example.notesreferences.ui.SelectListener
import com.example.notesreferences.domain.NoteRepo
import com.example.notesreferences.impl.NoteRepoImpl
import androidx.recyclerview.widget.RecyclerView
import android.database.sqlite.SQLiteDatabase
import com.example.notesreferences.ui.NotesAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.notesreferences.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesreferences.MainActivity
import androidx.fragment.app.FragmentResultListener
import com.example.notesreferences.domain.NoteEntity
import android.widget.Toast
import com.example.notesreferences.categoryFragments.CategoryLongTermFragment
import android.content.ContentValues
import android.content.Context
import com.example.notesreferences.EditNoteFragment
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment

class CategoryLongTermFragment : Fragment(), SelectListener {
    var noteRepo: NoteRepo = NoteRepoImpl()
    var recyclerView: RecyclerView? = null
    var bd: SQLiteDatabase? = null
    private val adapter = NotesAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        readDataBase()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category_long_term, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recycler_long_term)
        recyclerView?.setLayoutManager(LinearLayoutManager(context))
        recyclerView?.setAdapter(adapter)
        adapter.setData(noteRepo.notes())
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.setFragmentResultListener(
            MainActivity.DATA_LONG_TERM,
            this,
            FragmentResultListener { requestKey, result ->
                val title = result.getString("title")
                val description = result.getString("description")
                noteRepo.addNote(NoteEntity(title, description))
                DataBase(title, description)
                writeDataBase()
            })
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            0 -> Toast.makeText(context, "case 0", Toast.LENGTH_SHORT).show()
            1 -> {
                adapter.setData(noteRepo.removeAll())
                val clearCount = bd!!.delete(LONG_TERM_TABLE_NAME, null, null)
                Log.d("@@@ mylogs", "deleted rows count = $clearCount")
                Toast.makeText(context, "case 1", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onContextItemSelected(item)
    }

    fun DataBase(title: String?, description: String?) {
        bdHelper = BDHelper(
            context
        )
        val cv = ContentValues()
        bd = bdHelper!!.writableDatabase
        cv.put(MainActivity.TITLE_KEY, title)
        cv.put(MainActivity.DESCRIPTION_KEY, description)
        bd!!.insert(LONG_TERM_TABLE_NAME, null, cv)
        Log.d("@@@ mylogs", "Create note. Title: $title Description: $description")
    }

    fun readDataBase() {
        bdHelper = BDHelper(
            context
        )
        bd = bdHelper!!.readableDatabase
        val c = bd!!.query(LONG_TERM_TABLE_NAME, null, null, null, null, null, null)
        if (c.moveToFirst()) {
            val columnID = c.getColumnIndex("id")
            val columnTitle = c.getColumnIndex(MainActivity.TITLE_KEY)
            val columnDescription = c.getColumnIndex(MainActivity.DESCRIPTION_KEY)
            do {
                noteRepo.addNote(
                    NoteEntity(
                        c.getString(columnTitle),
                        c.getString(columnDescription)
                    )
                )
            } while (c.moveToNext())
        } else {
            Log.d("@@@ mylogs", "That's all")
        }
        c.close()
    }

    //    int clearCount = db.delete("mytable", null, null);
    //      Log.d(LOG_TAG, "deleted rows count = " + clearCount);
    fun writeDataBase() {
        val c = bd!!.query(LONG_TERM_TABLE_NAME, null, null, null, null, null, null)
        if (c.moveToFirst()) {
            val columnID = c.getColumnIndex("id")
            val columnTitle = c.getColumnIndex(MainActivity.TITLE_KEY)
            val columnDescription = c.getColumnIndex(MainActivity.DESCRIPTION_KEY)
            do {
                Log.d(
                    "@@@ mylogs",
                    "Table: " + LONG_TERM_TABLE_NAME + "Note № " + c.getInt(columnID) +
                            " Title: " + c.getString(columnTitle) +
                            " Description: " + c.getString(columnDescription)
                )
            } while (c.moveToNext())
        } else {
            Log.d("@@@ mylogs", "That's all")
        }
        c.close()
    }

    override fun onItemClicked(noteEntity: NoteEntity) {
        Toast.makeText(context, noteEntity.title, Toast.LENGTH_SHORT).show()
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, EditNoteFragment())
            .addToBackStack(null)
            .commit()
        val result = Bundle()
        result.putString("title", noteEntity.title)
        result.putString("description", noteEntity.detale)
        //        result.putSerializable("gg", (Serializable) noteEntity);
        parentFragmentManager.setFragmentResult(MainActivity.DATA_T0_EDIT, result)
    }

    class BDHelper(context: Context?) : SQLiteOpenHelper(context, LONG_TERM_TABLE_NAME, null, 1) {
        override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
            sqLiteDatabase.execSQL(
                "create table " + LONG_TERM_TABLE_NAME + " ("
                        + "id integer primary key autoincrement,"
                        + "description text,"
                        + "title text" + ");"
            )
        }

        override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {}
    }

    companion object {
        const val LONG_TERM_TABLE_NAME = "LongTermTable"
        var bdHelper: BDHelper? = null
    }
}