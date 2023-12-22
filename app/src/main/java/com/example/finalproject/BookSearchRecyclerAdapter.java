package com.example.finalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookSearchRecyclerAdapter
        extends RecyclerView.Adapter<BookSearchRecyclerAdapter.BookSearchViewHoder>
implements NetworkingManager.NetworkingInterfaceListener{

    Context context;
    ArrayList<Books> bookSearch_list;
    NetworkingManager networkingManager = new NetworkingManager();
    public BookSearchRecyclerAdapter(Context context, ArrayList<Books> bookSearch_list) {
        this.context = context;
        this.bookSearch_list = bookSearch_list;
    }
    interface SearchBookClickListner{
        void searchBookSelected(Books selectedBook);

    }
    SearchBookClickListner listener;
    @Override
    public void networkingFinishWithJsonString(String json) {

    }

    @Override
    public void networkingFinishWithBitMapImage(Bitmap bitmap) {

    }

    public class BookSearchViewHoder extends RecyclerView.ViewHolder{
        public BookSearchViewHoder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public BookSearchViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.search_row,parent,false);
        return new BookSearchViewHoder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookSearchViewHoder holder, int position) {

        TextView author = holder.itemView.findViewById(R.id.searchAuthor);
        TextView title = holder.itemView.findViewById(R.id.searchTitle);
        TextView textSnip = holder.itemView.findViewById(R.id.searchSnip);

        author.setText("by "+bookSearch_list.get(position).getAuthor());
        title.setText(bookSearch_list.get(position).getTitle());
       // textSnip.setText(bookSearch_list.get(position).getTextSnippet());



        ImageView searchBookImg = holder.itemView.findViewById(R.id.searchBookImg);


        Picasso.get().load(bookSearch_list.get(position)
                .getBookImagelink())
                .placeholder(R.drawable.loading)
                .into(searchBookImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.searchBookSelected(bookSearch_list.get(holder.getAdapterPosition()));
            }
        });



    }

    @Override
    public int getItemCount() {
        return bookSearch_list.size();
    }


}
