package com.example.finalproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements CreateNewListFragment.AddListFragmentListener,
        DatabaseManager.DatabaseListener,
        ListAdapter.AdapterListner
{
    RecyclerView readingList;
    FloatingActionButton addList;
    ListAdapter adapter;
    DatabaseManager db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("My Reading Lists");
        setContentView(R.layout.activity_main);
        db = ((MyApp)getApplication()).dbm;
        db.listener = this;
        db.getDB(this);

        db.getAllLists();


        readingList = findViewById(R.id.listCategories);

        addList = findViewById(R.id.addListCategories);

        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewListFragment fragment = new CreateNewListFragment();
                fragment.listener = MainActivity.this;
                fragment.show(getSupportFragmentManager().beginTransaction(),"1");
            }
        });


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(readingList);


    }
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.DOWN, ItemTouchHelper.LEFT |
            ItemTouchHelper.RIGHT |
            ItemTouchHelper.DOWN |
            ItemTouchHelper.UP) {

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

            int position = viewHolder.getAdapterPosition();
            String title = adapter.listcat.get(position).listName;

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Delete Confirmation");
            builder.setMessage("Are you sure you want to delete " + title + "?");

            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // User clicked Delete, perform deletion
                    db.deleteOneList(adapter.listcat.get(position));
                    Toast.makeText(MainActivity.this, title + " list has been deleted", Toast.LENGTH_SHORT).show();
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    adapter.notifyItemChanged(position);
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
    };


    @Override
    public void addNewList(String listName, String listdescrip) {
        if (!listName.isEmpty()) {
            ListClass l = new ListClass();
            l.listName = listName;
            l.listDescrip = listdescrip;
            db.insertNewList(l);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void DBManagerGetAllList(List<ListClass> list) {
        if (adapter == null) {
            adapter = new ListAdapter(this, list);
            adapter.listner = this;
        }
        adapter.listcat = list;
        readingList.setAdapter(adapter);
        readingList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void UpdatingIsDone() {
        db.getAllLists();

    }

    @Override
    public void booksForOneList(BookListRelationship r) {
    }

    @Override
    public void ListAdapterClickListener(ListClass selectedList) {
        Intent toBooks = new Intent(this,BookSearchRecyclerActivity.class);
        toBooks.putExtra("listId",selectedList.listID);
        toBooks.putExtra("listName",selectedList.listName);
        startActivity(toBooks);

    }
}