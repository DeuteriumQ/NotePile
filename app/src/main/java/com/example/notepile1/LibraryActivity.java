package com.example.notepile1;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.notepile1.database.AppDatabase;
import com.example.notepile1.database.NotebookDao;
import com.example.notepile1.database.PageDao;
import com.example.notepile1.models.Notebook;
import com.example.notepile1.models.Page;

import java.util.ArrayList;
import java.util.List;

public class LibraryActivity extends AppCompatActivity {

    private String m_Text ="";
    private LibraryAdapter libraryAdapter;
    private List<Notebook> notebooks;
    private List<Page> pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                        long newId = notebookDao.insert(notebook);
                        notebooks.add(notebook);

                        libraryAdapter.notifyItemInserted(notebooks.size());

                        for(int i = 0; i < 100; i++) {
                            Page page = new Page(i);
                            page.bookId = newId;
                            page.setHTMLtext("<div> Hello! " + String.valueOf(i) + "</div>");
                            pageDao.insert(page);
                            Log.d("PageID:", " " + newId);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
