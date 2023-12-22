package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BookSearchRecyclerActivity extends AppCompatActivity
implements NetworkingManager.NetworkingInterfaceListener,
        BookSearchRecyclerAdapter.SearchBookClickListner,
        SearchBookSelectedFragment.AddBookFragmentListener,
        DatabaseManager.DatabaseListener,
        BookAdapter.AlertDialogListner
{
    JsonManager jsonManager;
    NetworkingManager networkingManger;
    BookSearchRecyclerAdapter adapter;
    ArrayList<Books> bookSearch_list =  new ArrayList<>(0);
    RecyclerView recyclerView;
    //
    int id;
    RecyclerView booklist;
    ArrayList<BookClass> bookArrayList = new ArrayList<>(0);
    BookAdapter bookadapter;
    BookListRelationship ListBooksObject;
    DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);


        id = getIntent().getExtras().getInt("listId");
        String name = getIntent().getExtras().getString("listName");
        this.setTitle(name);

        db = ((MyApp) getApplication()).dbm;
        db.listener = this;
        db.getAllBooksForOneList(id);
        bookadapter = new BookAdapter(this, bookArrayList);

        bookadapter.listner=this;


        booklist = findViewById(R.id.bookSearchList);

        booklist.setAdapter(bookadapter);
        booklist.setLayoutManager(new LinearLayoutManager(this));

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(booklist);




    }
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.DOWN, ItemTouchHelper.LEFT |
                ItemTouchHelper.RIGHT |
                ItemTouchHelper.DOWN |
                ItemTouchHelper.UP) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                int position = viewHolder.getAdapterPosition();
                String title = bookadapter.bookList.get(position).title.toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(BookSearchRecyclerActivity.this);
                builder.setTitle("Delete Confirmation");
                builder.setMessage("Are you sure you want to delete " + title + "?");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteOneBook(bookadapter.bookList.get(position));
                        Toast.makeText(BookSearchRecyclerActivity.this, title+" has been deleted from the list", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bookadapter.notifyItemChanged(position);
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        };



    private void setupSearchRecyclerAdapter() {
        bookSearch_list.clear();
        bookSearch_list = ((MyApp) getApplication()).bookSearch_list;


        adapter = new BookSearchRecyclerAdapter(this, bookSearch_list);

        recyclerView = findViewById(R.id.bookSearchList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.listener = this;

        networkingManger = new NetworkingManager();
        networkingManger.listener = this;
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu,menu);
        SearchView menuSearchItem = (SearchView) menu.findItem(R.id.searchbar_menu_item).getActionView();



        menuSearchItem.setQueryHint("Search for a Book");
        menuSearchItem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                setupSearchRecyclerAdapter();
                networkingManger.searchBooks(query);
                return false;

            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void networkingFinishWithJsonString(String json) {

        JsonManager jsonManager = new JsonManager();

        ArrayList<Books> searchBooksList = jsonManager.fromJsonToSearchBooksList(json);

        if (searchBooksList.size()==0) {
            Toast.makeText(BookSearchRecyclerActivity.this, "Sorry, couldn't find what you're looking for", Toast.LENGTH_SHORT).show();
               }
        bookSearch_list.clear();
        bookSearch_list.addAll(searchBooksList);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void networkingFinishWithBitMapImage(Bitmap bitmap) {
    }
    @Override
    public void searchBookSelected(Books selectedBook) {

        Bundle args = new Bundle();
        args.putParcelable("selectedBook", selectedBook);

        SearchBookSelectedFragment fragment = new SearchBookSelectedFragment();
        fragment.setArguments(args);

        fragment.listener=this;
        fragment.show(getSupportFragmentManager(), "SelectedBookFragment");

        }


    @Override
    public void addNewBook(String title, String author, String publishedDate, String description, String bookImagelink) {
            BookClass book = new BookClass();

            book.title = title;
            book.author = author;
            book.publishedDate = publishedDate;
            book.description = description;
            book.bookImagelink = bookImagelink;
            book.list_bookID = id;
            db.insertNewBook(book);

        db.getAllBooksForOneList(id);

        recyclerView.setAdapter(bookadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(BookSearchRecyclerActivity.this));

    }

    @Override
    public void DBManagerGetAllList(List<ListClass> list) {

    }

    @Override
    public void UpdatingIsDone() {db.getAllBooksForOneList(id);

    }

    @Override
    public void booksForOneList(BookListRelationship r) {
        if (r != null) {
            bookArrayList = (ArrayList<BookClass>) r.allBooks;
            bookadapter.bookList = r.allBooks;
           bookadapter.notifyDataSetChanged();
        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        db.getAllBooksForOneList(id);
        bookadapter.notifyDataSetChanged();

    }

    @Override
    public void BookAdapterClickListener(BookClass selectedBook) {
        Intent i = new Intent(BookSearchRecyclerActivity.this, BookActivity.class);
        i.putExtra("selectedBook", selectedBook);
        startActivity(i);
        finish();
        Toast.makeText(this, "Book Clicked", Toast.LENGTH_SHORT).show();
    }


}