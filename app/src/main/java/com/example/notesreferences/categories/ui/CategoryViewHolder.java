package com.example.notesreferences.categories.ui;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesreferences.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    OnCategoryListener onCategoryListener;
    public CategoryViewHolder(@NonNull View itemView, OnCategoryListener onCategoryListener) {
        super(itemView);
        this.onCategoryListener = onCategoryListener;

        itemView.setOnClickListener(this);
    }

    public TextView item = itemView.findViewById(R.id.title_item);

    @Override
    public void onClick(View view) {
        Toast.makeText(item.getContext(), "toast", Toast.LENGTH_SHORT).show();
        onCategoryListener.onCategoryClick(getAdapterPosition());
    }

    public interface OnCategoryListener {
        void onCategoryClick(int position);
    }
}
