package com.example.notesreferences;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.notesreferences.categories.domain.CategoryEntity;
import com.example.notesreferences.categoryFragments.CategoryDayNoteFragment;
import com.example.notesreferences.categoryFragments.CategoryLongTermFragment;
import com.example.notesreferences.categoryFragments.CategoryProductListFragment;
import com.example.notesreferences.categoryFragments.CategoryTemporaryFragment;
import com.example.notesreferences.domain.NoteEntity;
import com.example.notesreferences.domain.NoteRepo;
import com.example.notesreferences.impl.NoteRepoImpl;
import com.example.notesreferences.ui.NotesAdapter;
import com.example.notesreferences.ui.SelectListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivityFragment extends Fragment implements SelectListener {

    RecyclerView recyclerView;
    RecyclerView recyclerItem;
    NoteRepo noteRepo = new NoteRepoImpl();
    private NotesAdapter adapter = new NotesAdapter(this);
//    private NoteAdapterMainFragment adapter1 = new NoteAdapterMainFragment();
    public final static String TABLE_NAME = "mytable";
    private MainActivity.BDHelper bdHelper;
    SQLiteDatabase bd;
    public List<CategoryEntity> categories = new ArrayList<>();
    private Map<Integer, Fragment> fragments = new HashMap<>();
    private List<Integer> notesList = new ArrayList<>();
    Map<Integer, Fragment> fragmentMap = new HashMap<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.main_fragment_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setData(noteRepo.notes());

        fragmentMap.put(0, new CreateNoteFragment());



        categories.add(new CategoryEntity(1, "Day note"));
        categories.add(new CategoryEntity(2, "Long-term"));
        categories.add(new CategoryEntity(3, "Temporary"));
        categories.add(new CategoryEntity(3, "Product list"));
        categories.add(new CategoryEntity(3, "And something else"));

        setCategoryAdapter(categories);

        fragments.put(0, new CategoryDayNoteFragment());
        fragments.put(1, new CategoryLongTermFragment());
        fragments.put(2, new CategoryProductListFragment());
        fragments.put(3, new CategoryTemporaryFragment());


        super.onViewCreated(view, savedInstanceState);

        getParentFragmentManager().setFragmentResultListener(MainActivity.DATA_TO_MAIN, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String title = result.getString("title");
                String description = result.getString("description");

                noteRepo.addNote(new NoteEntity(title, description));
            }
        });
    }

    private void setCategoryAdapter(List<CategoryEntity> categoryEmptyList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);


    }

    @Override
    public void onItemClicked(NoteEntity noteEntity) {
        Toast.makeText(getContext(), noteEntity.getTitle(), Toast.LENGTH_SHORT).show();
    }
}