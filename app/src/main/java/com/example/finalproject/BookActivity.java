package com.example.finalproject;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BookActivity  extends AppCompatActivity {

        ImageView bookIcon;
        TextView title,author,description,publishedDate;
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.saved_book_selected_activity);
                BookClass selectedBook = getIntent().getParcelableExtra("selectedBook");

                bookIcon = findViewById(R.id.savedSelectedBook);
                title = findViewById(R.id.selectedSavedTitle);
                author = findViewById(R.id.selectedSavedAuthor);
                description = findViewById(R.id.savedlongDescrip);
                publishedDate = findViewById(R.id.savedpublishedDate);

                if (selectedBook != null) {
                        this.setTitle(selectedBook.title);
                        title.setText(selectedBook.title);
                        author.setText(selectedBook.author);
                        description.setText(selectedBook.description);
                        publishedDate.setText(selectedBook.publishedDate);

                        MyApp.mainhandler.post(new Runnable() {
                                @Override
                                public void run() {
                                        Picasso.get().load(selectedBook.bookImagelink)
                                                .placeholder(R.drawable.loading)
                                                .into(bookIcon);

                                }

                        });


                }
        }
}

