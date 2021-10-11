package com.example.notesreferences.ui;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesreferences.R;
import com.example.notesreferences.domain.NoteEntity;

import java.util.List;

public class NoteAdapterMainFragment  extends RecyclerView.Adapter<NoteViewHolderMainFragment>{

    private List<NoteEntity> data;

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<NoteEntity> data){
        this.data = data;
        notifyDataSetChanged();
    }



    private NoteEntity getItem(int position){
        return data.get(position);
    }

    @NonNull
    @Override
    public NoteViewHolderMainFragment onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_main_fragment, parent, false);
        return new NoteViewHolderMainFragment(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolderMainFragment holder, int position) {
        NoteEntity note = getItem(position);
        holder.titleTextView.setText(note.getTitle());
        holder.detailTextView.setText(note.getDetale());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
