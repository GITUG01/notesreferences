package com.example.notesreferences

import android.content.Context
import com.example.notesreferences.ui.SelectListener
import com.example.notesreferences.categories.domain.CategoryEntity
import androidx.recyclerview.widget.RecyclerView
import com.example.notesreferences.domain.NoteRepo
import com.example.notesreferences.impl.NoteRepoImpl
import android.database.sqlite.SQLiteDatabase
import com.example.notesreferences.ui.NotesAdapter
import com.example.notesreferences.MainActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import com.example.notesreferences.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesreferences.CreateNoteFragment
import com.example.notesreferences.categoryFragments.CategoryDayNoteFragment
import com.example.notesreferences.categoryFragments.CategoryLongTermFragment
import com.example.notesreferences.categoryFragments.CategoryProductListFragment
import com.example.notesreferences.categoryFragments.CategoryTemporaryFragment
import androidx.fragment.app.FragmentResultListener
import com.example.notesreferences.domain.NoteEntity
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.util.ArrayList
import java.util.HashMap

internal interface closeApp {
    fun close()
}

class MainActivityFragment : Fragment(), OnBackButton, SelectListener {
    var categories: MutableList<CategoryEntity> = ArrayList()
    var recyclerView: RecyclerView? = null
    var recyclerItem: RecyclerView? = null
    var noteRepo: NoteRepo = NoteRepoImpl()
    var bd: SQLiteDatabase? = null
    var fragmentMap: MutableMap<Int, Fragment> = HashMap()
    var running = false
    private val adapter = NotesAdapter(this)
    private val bdHelper: MainActivity.BDHelper? = null
    private val fragments: MutableMap<Int, Fragment> = HashMap()
    private val notesList: List<Int> = ArrayList()
    private var onBackButton: OnBackButton? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBackButton = context as OnBackButton
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.main_fragment_recycler)
        recyclerView?.adapter
        recyclerView?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
        adapter.setData(noteRepo.notes())
        fragmentMap[0] = CreateNoteFragment()
        categories.add(CategoryEntity(1, "Day note"))
        categories.add(CategoryEntity(2, "Long-term"))
        categories.add(CategoryEntity(3, "Temporary"))
        categories.add(CategoryEntity(3, "Product list"))
        categories.add(CategoryEntity(3, "And something else"))
        setCategoryAdapter(categories)
        fragments[0] = CategoryDayNoteFragment()
        fragments[1] = CategoryLongTermFragment()
        fragments[2] = CategoryProductListFragment()
        fragments[3] = CategoryTemporaryFragment()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setCategoryAdapter(categoryEmptyList: List<CategoryEntity>) {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    }

    override fun onItemClicked(noteEntity: NoteEntity) = Toast.makeText(context, noteEntity.title, Toast.LENGTH_SHORT).show()

    companion object {
        //    private NoteAdapterMainFragment adapter1 = new NoteAdapterMainFragment();
        const val TABLE_NAME = "mytable"
    }

}