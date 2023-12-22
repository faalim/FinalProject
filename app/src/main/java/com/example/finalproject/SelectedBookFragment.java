
package com.example.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class SelectedBookFragment extends DialogFragment implements NetworkingManager.NetworkingInterfaceListener {
    TextView rank, author, title, description;
    ImageView bookImage;
    NetworkingManager networkingManager;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.selectedbookoverview, null);


        networkingManager = new NetworkingManager();
        networkingManager.listener = this;

        rank = v.findViewById(R.id.rank);
        author = v.findViewById(R.id.fistTextView);
        title = v.findViewById(R.id.secTextView);
        description = v.findViewById(R.id.thirdTextView);
        bookImage = v.findViewById(R.id.selectedBookImg);

        // Retrieve the selected book from the arguments
        Bundle args = getArguments();
        if (args != null) {
            BookInfo selectedBook = args.getParcelable("selectedBook");

            // Now you can use the selected book to update the UI elements
            try {
                updateUI(selectedBook);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        builder.setView(v)
                .setPositiveButton("OK", (dialog, which) -> dismiss());

        return builder.create();
    }

    private void updateUI(BookInfo selectedBook) throws IOException {
        // Set the values to the UI elements
        rank.setText("Rank: " + selectedBook.getAward());
        author.setText("Author: " + selectedBook.getAuthor());
        title.setText("Title: " + selectedBook.getTitle());
        description.setText(selectedBook.getDescription());

        networkingManager.getSelectedImage(selectedBook.getBookicon());

    }

    @Override
    public void networkingFinishWithJsonString(String json) {

    }

    @Override
    public void networkingFinishWithBitMapImage(Bitmap bitmap) {
        bookImage.setImageBitmap(bitmap);

    }
}
