package com.example.notepile1.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Notebook {

    @PrimaryKey (autoGenerate = true)
    public long id;

    public String name;

    public Notebook(String name ) {
        this.name = name;
    }
}
