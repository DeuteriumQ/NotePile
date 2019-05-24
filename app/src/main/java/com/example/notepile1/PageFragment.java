package com.example.notepile1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.notepile1.models.Page;

import jp.wasabeef.richeditor.RichEditor;


public class PageFragment extends Fragment {
    private RichEditor mEditor;
    private Page page;

    public PageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_page, container, false);



        mEditor = rootView.findViewById(R.id.editor);
        mEditor.setHtml("<p> Hi </p>");
        return rootView;
    }
}
