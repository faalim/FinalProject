package com.example.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class SearchBookSelectedFragment extends DialogFragment  {
    TextView author,title,date,description;
    Button addTomyList;
    ImageView bookImage;

    String imageLink;

    interface AddBookFragmentListener{
        void addNewBook(  String title,
        String author,
        String publishedDate,
        String description,
        String bookImagelink
);
    }

    AddBookFragmentListener listener;



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.selected_search_book, null);



        date = v.findViewById(R.id.publishedDate);
        author = v.findViewById(R.id.selectedSearchAuthor);
        title = v.findViewById(R.id.selectedSearchTitle);
        description = v.findViewById(R.id.longDescrip);
        bookImage = v.findViewById(R.id.searchSelectedBook);
        addTomyList = v.findViewById(R.id.addToList);

        // Retrieve the selected book from the arguments
        Bundle args = getArguments();
        if (args != null) {
            Books selectedBook = args.getParcelable("selectedBook");

            // Now you can use the selected book to update the UI elements
            try {
                updateUI(selectedBook);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        addTomyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.addNewBook(

                        title.getText().toString(),
                        author.getText().toString(),
                        date.getText().toString(),
                        description.getText().toString(),
                        imageLink.toString()

                        );
               dismiss();

            }

        });

        Button close = v.findViewById(R.id.exit);
        close.setOnClickListener(v1 -> dismiss());
        builder.setView(v)
                .setPositiveButton("", null);
        return builder.create();
    }
    private void updateUI(Books selectedBook) throws IOException {
        // Set the values to the UI elements
        date.setText(selectedBook.getPublishedDate());
        author.setText("by: " + selectedBook.getAuthor());
        title.setText( selectedBook.getTitle());
        description.setText("Description: " + selectedBook.getDescription());
        description.setMovementMethod(new ScrollingMovementMethod());


        Picasso.get().load(selectedBook.getBookImagelink())
                .placeholder(R.drawable.pink)
                .into(bookImage);
        imageLink = selectedBook.getBookImagelink();


    }


}
