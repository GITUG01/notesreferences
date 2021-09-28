package com.example.notesreferences.domain;

import androidx.annotation.Nullable;

public class NoteEntity {
    @Nullable
    private Integer id;
    private String title;
    private String detale;


    public NoteEntity(String title, String detale) {
        this.title = title;
        this.detale = detale;
    }
    @Nullable
    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetale() {
        return detale;
    }

    public void setDetale(String detale) {
        this.detale = detale;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }
}
