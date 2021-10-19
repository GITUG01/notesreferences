package com.example.notesreferences.ui;

import android.view.View;

import com.example.notesreferences.domain.NoteEntity;

public interface SelectListener {
    void onItemClicked(NoteEntity noteEntity);
}
