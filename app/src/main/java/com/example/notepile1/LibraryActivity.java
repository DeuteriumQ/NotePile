package com.example.notepile1;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import com.example.notepile1.database.AppDatabase;
import com.example.notepile1.database.NotebookDao;
import com.example.notepile1.database.PageDao;
import com.example.notepile1.models.Notebook;
import com.example.notepile1.models.Page;
import java.util.List;

public class LibraryActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1092;
    private String m_Text ="";
    private LibraryAdapter libraryAdapter;
    private List<Notebook> notebooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED  && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(LibraryActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }

        AppDatabase db = App.getInstance().getDatabase();
        final NotebookDao notebookDao = db.notebookDao();
        final PageDao pageDao = db.pageDao();


        notebooks = notebookDao.getAll();

        libraryAdapter = new LibraryAdapter(this, notebooks);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LibraryActivity.this);
                builder.setTitle("New notebook");

                final EditText input = new EditText(LibraryActivity.this);
                input.setHint(R.string.new_book_hint);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        Notebook notebook = new Notebook(m_Text);
                        long newID = notebookDao.insert(notebook);
                        notebook.id = newID;
                        notebooks.add(notebook);

                        libraryAdapter.notifyItemInserted(notebooks.size());

                        for(int i = 0; i < 100; i++) {
                            Page page = new Page(i);
                            page.bookId = newID;
                            pageDao.insert(page);
                        }


                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setAdapter(libraryAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(LibraryActivity.this);
            builder.setTitle("Are you sure you want to exit?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (Build.VERSION.SDK_INT < 21) {
                        finishAffinity(); //finish this and all parent activities
                    } else {
                        finishAndRemoveTask(); //finish activity and task, requires API 21
                    }
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }

        return super.onKeyDown(keyCode, event);
    }
}
