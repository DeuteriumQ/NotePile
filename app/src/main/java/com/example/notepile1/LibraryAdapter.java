package com.example.notepile1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notepile1.database.AppDatabase;
import com.example.notepile1.database.NotebookDao;
import com.example.notepile1.models.Notebook;

import java.util.ArrayList;
import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ItemHolder> {
    private Context context;
    private List<Notebook> list;

    public LibraryAdapter(Context context, List<Notebook> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public LibraryAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View view = inflater.inflate(R.layout.line_layout, viewGroup, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, final int position) {

        itemHolder.bookName.setText(list.get(position).name);
        AppDatabase db = App.getInstance().getDatabase();
        final NotebookDao notebookDao = db.notebookDao();

        itemHolder.linearLayout.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                Log.d("Adapter", " " + position);
                notebookDao.delete(list.get(position));
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, list.size());
                return true;
            }
        });

        itemHolder.linearLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), BookActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private  final TextView bookName;
        private final LinearLayout linearLayout;

        ItemHolder(View itemView) {
            super(itemView);
            bookName = itemView.findViewById(R.id.bookName);
            linearLayout = itemView.findViewById(R.id.line);
        }
    }
}
