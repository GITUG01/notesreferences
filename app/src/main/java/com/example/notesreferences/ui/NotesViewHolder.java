package com.example.notesreferences.ui;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesreferences.R;

public class NotesViewHolder extends RecyclerView.ViewHolder {

    public TextView titleTextView = itemView.findViewById(R.id.title_text);
    public TextView detailTextView = itemView.findViewById(R.id.detail_text);
    public CardView cardView = itemView.findViewById(R.id.note_container);

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);

    }

}
