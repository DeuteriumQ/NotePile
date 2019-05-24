package com.example.notepile1.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.notepile1.models.Notebook;

import java.util.List;

@Dao
public interface NotebookDao {

    @Query("SELECT * FROM notebook")
    List<Notebook> getAll();

    @Query("SELECT * FROM notebook WHERE id = :id")
    Notebook getById(long id);

    @Insert
    long insert(Notebook notebook);

    @Update
    void update(Notebook notebook);

    @Delete
    void delete(Notebook notebook);


}
