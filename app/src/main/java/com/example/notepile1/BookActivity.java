package com.example.notepile1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

public class BookActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 100;
    private PagerAdapter pagerAdapter;
    private ViewPager pager;
    private long docID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        docID = intent.getLongExtra("DOC_ID", -1);

        String bookName = intent.getStringExtra("BOOK_NAME");
        if (bookName != null)
            getSupportActionBar().setTitle(bookName);
        else getSupportActionBar().setTitle("null");

        pager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            PageFragment fragment = new PageFragment();
            Bundle bundle = new Bundle();
            bundle.putLong("DOC_ID", docID);
            bundle.putInt("POSITION", position);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_goto : {
                AlertDialog.Builder builder = new AlertDialog.Builder(BookActivity.this);
                builder.setTitle("Go to page");

                final EditText input = new EditText(BookActivity.this);
                input.setHint("1 to 100");
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int pageNum = Integer.parseInt(input.getText().toString()) - 1;
                        if(0 < pageNum && pageNum < 100)
                            pager.setCurrentItem(pageNum);
                        else pager.setCurrentItem(0);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
