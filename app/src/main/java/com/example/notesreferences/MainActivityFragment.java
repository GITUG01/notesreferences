package com.example.notesreferences;
//
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.notesreferences.categories.domain.CategoryEmpty;
//import com.example.notesreferences.categories.ui.CategoryAdapter;
//import com.example.notesreferences.categories.ui.CategoryViewHolder;
//import com.example.notesreferences.domain.NoteRepo;
//import com.example.notesreferences.impl.NoteRepoImpl;
//import com.example.notesreferences.ui.NotesAdapter;
//
//import java.io.ObjectInputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
//public class MainActivityFragment extends Fragment{
//
//    RecyclerView recyclerView;
//    RecyclerView recyclerItem;
//    NoteRepo noteRepo = new NoteRepoImpl();
//    private NotesAdapter adapter = new NotesAdapter();
//    public final static String TABLE_NAME = "mytable";
//    private MainActivity.BDHelper bdHelper;
//    SQLiteDatabase bd;
//    public List<CategoryEmpty> categories = new ArrayList<>();
//    private Map<Integer, Fragment> fragments = new HashMap<>();
//    private List<Integer> notesList = new ArrayList<>();
//    Map<Integer, Fragment> fragmentMap = new HashMap<>();
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_main_activity, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//
//        fragmentMap.put(0, new CreateNoteFragment());
//
////        recyclerView = view.findViewById();
////        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
////        recyclerView.setAdapter(adapter);
////        adapter.setData(noteRepo.notes());
//
//        super.onViewCreated(view, savedInstanceState);
//
//        categories.add(new CategoryEmpty(1, "Day note"));
//        categories.add(new CategoryEmpty(2, "Long-term"));
//        categories.add(new CategoryEmpty(3, "Temporary"));
//        categories.add(new CategoryEmpty(3, "Product list"));
//        categories.add(new CategoryEmpty(3, "And something else"));
//
//        setCategoryAdapter(categories);
//
//        fragments.put(0, new CategoryDayNoteFragment());
//        fragments.put(1, new CategoryLongTermFragment());
//        fragments.put(2, new CategoryProductListFragment());
//        fragments.put(3, new CategoryTemporaryFragment());
//    }
//
//    private void setCategoryAdapter(List<CategoryEmpty> categoryEmptyList) {
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
//
//        recyclerItem = requireView().findViewById(R.id.item_recycler);
//        recyclerItem.setLayoutManager(layoutManager);
//
//        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext(), categoryEmptyList, this);
//
//        recyclerItem.setAdapter(categoryAdapter);
//    }
//
//    @Override
//    public void onCategoryClick(int position) {
//
//    }
//
//    public interface gg{
//        void onCategoryClick();
//    }
////    @Override
////    public void onCategoryClick(int position) {
////        categories.get(position);
////        getSupportFragmentManager()
////                .beginTransaction()
////                .replace(R.id.fragment_container, Objects.requireNonNull(fragments.get(position)))
////                .commit();
////    }
//}