package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListBooksViewHolder> {
    private Context listCtx;
    public List<ListClass> listcat;

    interface AdapterListner {
        void ListAdapterClickListener(ListClass selectedList);
    }

    AdapterListner listner;
    public ListAdapter(Context listCtx,List<ListClass> listcat) {
        this.listCtx = listCtx;
        this.listcat = listcat;
    }

    class ListBooksViewHolder extends RecyclerView.ViewHolder
    implements View.OnClickListener{

        TextView listName,listDescrip;


        public ListBooksViewHolder(@NonNull View itemView) {
            super(itemView);

            listName = itemView.findViewById(R.id.listItemName);

            listDescrip = itemView.findViewById(R.id.descriptionList);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ListClass list = listcat.get(getAdapterPosition());
            listner.ListAdapterClickListener(list);

        }
    }

    @NonNull
    @Override
    public ListBooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(listCtx).inflate(R.layout.list_item, parent, false);
        return new ListBooksViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ListBooksViewHolder holder, int position) {
        if (listner != null) {
            ListClass t = listcat.get(position);


            holder.listName.setText(t.listName);
            if (t.listDescrip != null && !t.listDescrip.isEmpty()) {
                holder.listDescrip.setText(t.listDescrip);
            } else {
                holder.listDescrip.setText("");
            }
        }
    }
    @Override
    public int getItemCount() {
        return listcat.size();
    }



}
