package com.example.notesreferences.ui;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesreferences.CreateNoteFragment;
import com.example.notesreferences.MainActivity;
import com.example.notesreferences.R;
import com.example.notesreferences.domain.NoteEntity;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    private List<NoteEntity> data;

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

    }

    private NoteEntity getItem(int position){
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
