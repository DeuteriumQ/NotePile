package com.example.notepile1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.notepile1.database.AppDatabase;
import com.example.notepile1.database.PageDao;
import com.example.notepile1.models.Page;
import java.io.IOException;
import jp.wasabeef.richeditor.RichEditor;

import static android.app.Activity.RESULT_OK;


public class PageFragment extends Fragment {
    private RichEditor mEditor;
    private ImageView imageView;
    private Page page;
    private PageDao pageDao;
    private long docID;
    private ViewGroup rootView;
    private int num;
    private static final int PICK_IMAGE = 100;
    private Uri newUri = null;

    public PageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_page, container, false);

        docID = getArguments().getLong("DOC_ID", -1);
        num = getArguments().getInt("POSITION", -1);

        setHasOptionsMenu(true);

        AppDatabase db = App.getInstance().getDatabase();
        pageDao = db.pageDao();

        page = pageDao.getPageByIdAndNum(docID, num);

        mEditor = rootView.findViewById(R.id.editor);
        mEditor.setEditorFontSize(22);
        mEditor.setEditorHeight(600);
        mEditor.setPadding(10, 10, 10, 10);
        if (page != null)
            mEditor.setHtml(page.getHTMLtext());
        else mEditor.setHtml("<div> Page not loaded! </div>");

        setHasOptionsMenu(true);

        imageView = rootView.findViewById(R.id.imageview);
        imageView.setImageDrawable(null);

        if (page.getImageUri() != null){
            Uri oldUri = Uri.parse(page.getImageUri());
            generateBitmapImage(oldUri);
        }

        Log.d("Debg", page.getImageUri() + " ");


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bold : {
                mEditor.setBold();
                return true;
            }
            case R.id.action_italic : {
                mEditor.setItalic();
                return true;
            }
            case R.id.action_underline : {
                mEditor.setUnderline();
                return true;
            }
            case R.id.action_list : {
                mEditor.setBullets();
                return true;
            }
            case R.id.action_addimage : {
                setPickImage();
                return  true;
            }

            case R.id.action_removeimage : {
                imageView.setImageDrawable(null);
                page.setImageUri(null);
                pageDao.update(page);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void generateBitmapImage(Uri uri) {
        try {
            Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
            imageView.setImageBitmap(bitmapImage);
        } catch (IOException e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getContext(), "Image has been deleted from device", Toast.LENGTH_SHORT);
            toast.show();
            page.setImageUri(null);
            pageDao.update(page);
        }
    }

    public void setPickImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(photoPickerIntent, PICK_IMAGE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super method removed
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                try {
                    newUri = data.getData();
                    Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), newUri);
                    imageView.setImageBitmap(bitmapImage);
                    page.setImageUri(newUri.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Problem", Toast.LENGTH_SHORT);
                }


            }
        }
    }
}
