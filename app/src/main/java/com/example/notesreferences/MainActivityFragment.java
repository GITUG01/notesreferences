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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.notesreferences.categories.domain.CategoryEmpty;
import com.example.notesreferences.categories.ui.CategoryAdapter;
import com.example.notesreferences.categories.ui.CategoryViewHolder;
import com.example.notesreferences.domain.NoteEntity;
import com.example.notesreferences.domain.NoteRepo;
import com.example.notesreferences.impl.NoteRepoImpl;
import com.example.notesreferences.ui.NoteAdapterMainFragment;
import com.example.notesreferences.ui.NotesAdapter;

import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivityFragment extends Fragment{

    RecyclerView recyclerView;
    RecyclerView recyclerItem;
    NoteRepo noteRepo = new NoteRepoImpl();
    private NotesAdapter adapter = new NotesAdapter();
    private NoteAdapterMainFragment adapter1 = new NoteAdapterMainFragment();
    public final static String TABLE_NAME = "mytable";
    private MainActivity.BDHelper bdHelper;
    SQLiteDatabase bd;
    public List<CategoryEmpty> categories = new ArrayList<>();
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
        recyclerView.setAdapter(adapter1);
        adapter1.setData(noteRepo.notes());

        fragmentMap.put(0, new CreateNoteFragment());



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


//        LinearLayout layout = new LinearLayout(getContext());
//        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        layout.setPadding(5, 5, 5, 5);
//
//        TextView tv = new TextView(getContext());
//        tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        tv.setText("Some text");
//
//        layout.addView(tv);
//        recyclerView.addView(layout);

        noteRepo.addNote(new NoteEntity("title", "description"));

        super.onViewCreated(view, savedInstanceState);

        getParentFragmentManager().setFragmentResultListener(MainActivity.DATA_DAY_NOTE_TO_MAIN, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String title = (String) result.get("title");
                String description = (String) result.get("description");

                noteRepo.addNote(new NoteEntity(title, description));
            }
        });
    }

    private void setCategoryAdapter(List<CategoryEmpty> categoryEmptyList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);


    }
}