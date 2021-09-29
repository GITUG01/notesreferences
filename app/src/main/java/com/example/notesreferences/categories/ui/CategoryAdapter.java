package com.example.notesreferences.categories.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesreferences.R;
import com.example.notesreferences.categories.domain.CategoryEmpty;
import com.example.notesreferences.domain.NoteEntity;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    Context context;
    List<CategoryEmpty> categoryData;

    public CategoryAdapter(Context context, List<CategoryEmpty> categoryEmptyList) {
        this.context = context;
        this.categoryData = categoryEmptyList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CategoryEmpty category = getItem(position);
        holder.item.setText(category.getTitle());
    }

    private CategoryEmpty getItem(int position){
        return categoryData.get(position);
    }

    @Override
    public int getItemCount() {
        return categoryData.size();
    }
}
