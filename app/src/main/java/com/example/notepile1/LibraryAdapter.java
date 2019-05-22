package com.example.notepile1;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ItemHolder> {
    private Context context;
    private ArrayList<String> list;

    public LibraryAdapter(Context context, ArrayList<String> list) {
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
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int position) {

        itemHolder.bookName.setText(list.get(position));

        itemHolder.linearLayout.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(context, "Clicked long!", Toast.LENGTH_SHORT).show();
                //delete operation dialog etc.
                //actually get to itemholder
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
