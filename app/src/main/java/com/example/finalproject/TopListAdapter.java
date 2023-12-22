
package com.example.finalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TopListAdapter extends
            RecyclerView.Adapter<TopListAdapter.IndividualBookViewHolder>
            implements NetworkingManager.NetworkingInterfaceListener{

    private ArrayList<BookInfo> bookList;
    private Context context;
    private NetworkingManager networkingManager = new NetworkingManager();

    public TopListAdapter(ArrayList<BookInfo> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

        @Override
        public void networkingFinishWithJsonString(String json) {

        }

        @Override
        public void networkingFinishWithBitMapImage(Bitmap bitmap) {

        }
        interface IndvBookClickListener{
            void bookSelected(BookInfo selectedBook);
        }
        IndvBookClickListener listener;
        class IndividualBookViewHolder extends RecyclerView.ViewHolder{
            public IndividualBookViewHolder(@NonNull View itemView) {
                super(itemView);
            }
        }
        @NonNull
        @Override
        public IndividualBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.individualbooks,parent,false);
            return  new IndividualBookViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull TopListAdapter.IndividualBookViewHolder holder, int position) {
            ImageView bookImg = holder.itemView.findViewById(R.id.bookImg);

            MyApp.mainhandler.post(new Runnable() {
                @Override
                public void run() {
                    Picasso.get().load(bookList.get(holder.getAdapterPosition()).getBookicon())
                            .placeholder(R.drawable.loading)
                            .into(bookImg);

                }

            } );
//            Picasso.get().load(bookList.get(position).getBookicon())
//                    .placeholder(R.drawable.pink)
//                    .into(bookImg);



            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.bookSelected(bookList.get(holder.getAdapterPosition()));
                }
            });
        }

        @Override
        public int getItemCount() {
            return  bookList.size();
        }
    }

