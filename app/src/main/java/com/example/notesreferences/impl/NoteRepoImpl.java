package com.example.notesreferences.impl;

import com.example.notesreferences.domain.NoteEntity;
import com.example.notesreferences.domain.NoteRepo;

import java.util.ArrayList;
import java.util.List;

public class NoteRepoImpl implements NoteRepo {

    private ArrayList<NoteEntity> cache = new ArrayList<>();
    private int counter = 0;

    @Override
    public List<NoteEntity> notes() {
        return cache;
    }

    @Override
    public Integer addNote(NoteEntity note) {
        int newId = ++counter;
        note.setId(counter);
        cache.add(note);
        return newId;
    }

    @Override
    public boolean removeNote(int id) {
        for (int i = 0; i < cache.size(); i++) {
            if (cache.get(i).getId() == id){
                cache.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<NoteEntity> removeAll() {
        return cache = new ArrayList<>();
    }

    @Override
    public boolean editNote(int id, NoteEntity note) {
        removeNote(id);
        note.setId(id);
        cache.add(note);
        return true;
    }
}
