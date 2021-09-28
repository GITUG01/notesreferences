package com.example.notesreferences.impl;

import com.example.notesreferences.domain.NoteEntity;
import com.example.notesreferences.domain.NoteRepo;

import java.util.ArrayList;
import java.util.List;

public class NoteRepoImpl implements NoteRepo {

    private ArrayList<NoteEntity> cashe = new ArrayList<>();
    private int counter = 0;

    @Override
    public List<NoteEntity> notes() {
        return cashe;
    }

    @Override
    public Integer addNote(NoteEntity note) {
        int newId = ++counter;
        note.setId(counter);
        cashe.add(note);
        return newId;
    }

    @Override
    public boolean removeNote(int id) {
        for (int i = 0; i < cashe.size(); i++) {
            if (cashe.get(i).getId() == id){
                cashe.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean editNote(int id, NoteEntity note) {
        removeNote(id);
        note.setId(id);
        cashe.add(note);
        return true;
    }
}
