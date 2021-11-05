package com.example.notesreferences.ui;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesreferences.R;
import com.example.notesreferences.domain.NoteEntity;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> implements View.OnCreateContextMenuListener {
    private List<NoteEntity> data;
    private SelectListener selectListener;

    public NotesAdapter(SelectListener selectListener) {
        this.selectListener = selectListener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<NoteEntity> data){
        this.data = data;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        NoteEntity note = getItem(position);
        holder.titleTextView.setText(note.getTitle());
        holder.detailTextView.setText(note.getDetale());

        holder.cardView.setOnClickListener(view -> selectListener.onItemClicked(getItem(position)));
//
//        holder.cardView.setOnLongClickListener(view -> {
//            selectListener.onLongItemClicked(getItem(position));
//            return true;
//        });

        holder.cardView.setOnCreateContextMenuListener(this);

    }

    private NoteEntity getItem(int position){
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        menu.add(1, 0, 1, "Remove");
        menu.add(0, 1, 2, "Remove all");
    }
}
