package com.example.notepile1.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.notepile1.models.Page;

@Dao
public interface PageDao {

    @Insert
    void insert(Page page);

    @Update
    void update(Page page);

    @Delete
    void delete(Page page);

    @Query("SELECT * FROM page WHERE bookId=:bookId AND pageNum=:pageNum")
    Page getPageByIdAndNum(long bookId, int pageNum);
}
