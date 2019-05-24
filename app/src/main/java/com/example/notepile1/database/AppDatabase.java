package com.example.notepile1.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.notepile1.models.Notebook;
import com.example.notepile1.models.Page;

@Database(entities = {Notebook.class, Page.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NotebookDao notebookDao();
    public abstract PageDao pageDao();
}
