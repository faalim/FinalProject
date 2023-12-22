package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ListBooksViewHolder> {

    private Context mCtx;
    public List<BookClass> bookList;


    interface AlertDialogListner {
        void BookAdapterClickListener(BookClass selectedBook);
    }
    AlertDialogListner listner;

    public BookAdapter(Context mCtx, List<BookClass> bookList) {
        this.mCtx = mCtx;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public ListBooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.search_row, parent, false);
        return new ListBooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.ListBooksViewHolder holder, int position) {
        BookClass b = bookList.get(position);
//        TextView author = holder.itemView.findViewById(R.id.searchAuthor);
//        TextView title = holder.itemView.findViewById(R.id.searchTitle);
//
       holder.author.setText(b.author);
        holder.title.setText(b.title);


//        ImageView searchBookImg = holder.itemView.findViewById(R.id.searchBookImg);
        Picasso.get().load(b.bookImagelink)
                .placeholder(R.drawable.pink)
                .into(holder.searchBookImg);

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class ListBooksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView author, title;
        ImageView searchBookImg;
        public ListBooksViewHolder(@NonNull View itemView) {
            super(itemView);
             author = itemView.findViewById(R.id.searchAuthor);
             title = itemView.findViewById(R.id.searchTitle);
             searchBookImg = itemView.findViewById(R.id.searchBookImg);
             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     BookClass book = bookList.get(getAdapterPosition());
                     listner.BookAdapterClickListener(book);
                 }
             });
        }

        @Override
        public void onClick(View v) {

        }


//        @Override
//        public void onClick(View v) {
//            BookClass book = bookList.get(getAdapterPosition());
//            listner.BookAdapterClickListener(book);
//
//        }
    }
}
