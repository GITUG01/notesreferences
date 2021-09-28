package com.example.notesreferences.domain;

import java.util.List;

public interface NoteRepo {
    List<NoteEntity> notes();
    Integer addNote(NoteEntity note);
    boolean removeNote(int id);
    boolean editNote(int id, NoteEntity note);
}
