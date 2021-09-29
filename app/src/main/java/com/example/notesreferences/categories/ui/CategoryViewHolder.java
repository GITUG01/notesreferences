package com.example.notesreferences.categories.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesreferences.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {
    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public TextView item = itemView.findViewById(R.id.title_item);

}
