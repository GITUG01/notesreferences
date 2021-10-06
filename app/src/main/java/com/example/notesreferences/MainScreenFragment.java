package com.example.notesreferences;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notesreferences.categories.domain.CategoryEmpty;
import com.example.notesreferences.categories.ui.CategoryAdapter;
import com.example.notesreferences.domain.NoteRepo;
import com.example.notesreferences.impl.NoteRepoImpl;
import com.example.notesreferences.ui.NotesAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainScreenFragment extends Fragment {

    private Toolbar toolbar;
    RecyclerView recyclerView;
    RecyclerView recyclerItem;


    NoteRepo noteRepo = new NoteRepoImpl();
    private NotesAdapter adapter = new NotesAdapter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        toolbar = view.findViewById(R.id.my_toolbar);
//        setSupportActionBar(toolbar);
//
//        recyclerView = view.findViewById(R.id.recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);
//        adapter.setData(noteRepo.notes());
//
//        List<CategoryEmpty> categories = new ArrayList<>();
//        categories.add(new CategoryEmpty(1, "Day note"));
//        categories.add(new CategoryEmpty(2, "Long-term"));
//        categories.add(new CategoryEmpty(3, "Temporary"));
//        categories.add(new CategoryEmpty(3, "Product list"));
//        categories.add(new CategoryEmpty(3, "And something else"));
//
//        setCategoryAdapter(categories);

    }


}

