package com.example.notepile1.models;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;

@Entity(foreignKeys = @ForeignKey(entity = Notebook.class,
        parentColumns = "id",
        childColumns = "bookId",
        onDelete = ForeignKey.CASCADE))
public class Page {

    @PrimaryKey (autoGenerate = true)
    public long id;

    public final int pageNum;

    public String HTMLtext;

    public long bookId;

    public Page(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setHTMLtext(String HTMLtext) {
        this.HTMLtext = HTMLtext;
    }

    public String getHTMLtext() {
        return HTMLtext;
    }

    public String imageUri = null;

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }
}
