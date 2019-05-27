package com.example.notepile1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.notepile1.database.AppDatabase;
import com.example.notepile1.database.PageDao;
import com.example.notepile1.models.Page;

import jp.wasabeef.richeditor.RichEditor;


public class PageFragment extends Fragment {
    private RichEditor mEditor;
    private Page page;
    private PageDao pageDao;
    private long docID;

    public PageFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_page, container, false);

        docID = getArguments().getLong("DOC_ID", -1);
        int num = getArguments().getInt("POSITION", -1);
        Log.d("Pagedeb", " " + docID);

        AppDatabase db = App.getInstance().getDatabase();
        pageDao = db.pageDao();

        page = pageDao.getPageByIdAndNum(docID, num);

        mEditor = rootView.findViewById(R.id.editor);
        mEditor.setEditorFontSize(22);
        mEditor.setEditorHeight(600);
        mEditor.setPadding(10, 10, 10, 10);
        if(page != null)
            mEditor.setHtml(page.getHTMLtext());
        else mEditor.setHtml("<div> Page not loaded! </div>");
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(page != null) {
            page.setHTMLtext(mEditor.getHtml());
            pageDao.update(page);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(page != null) {
            page.setHTMLtext(mEditor.getHtml());
            pageDao.update(page);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(page != null) {
            page.setHTMLtext(mEditor.getHtml());
            pageDao.update(page);
        }
    }
}
