package com.example.finalproject;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseManager {
    BookListDatabase db;
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    Handler mainLooperHandler = new Handler(Looper.getMainLooper());

    interface DatabaseListener {
        void DBManagerGetAllList(List<ListClass> list);
        void UpdatingIsDone();
        void booksForOneList(BookListRelationship r);
    }

    DatabaseListener listener;

    BookListDatabase getDB (Context context){

        if (db == null)
            db = Room.databaseBuilder(context,
                    BookListDatabase.class, "database-name").build();
        return db;
    }

    void insertNewBook(BookClass book){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                db.getDao().addNewBookForOneList(book);
                mainLooperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.UpdatingIsDone();
                    }
                });
            }
        });
    }

    void insertNewList(ListClass list){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                db.getDao().addNewList(list);
                mainLooperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.UpdatingIsDone();
                    }
                });
            }
        });
    }
    void getAllLists(){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<ListClass> list = db.getDao().getAllLists();
                mainLooperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.DBManagerGetAllList(list);
                    }
                });
            }
        });
    }

    void deleteOneBook(BookClass b){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                db.getDao().deleteOneBook(b);
                mainLooperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.UpdatingIsDone();
                    }
                });
            }
        });
    }


    void deleteOneList(ListClass l){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
               db.getDao().deleteBooksForList(l.listID);
                db.getDao().deleteOneList(l);
                mainLooperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.UpdatingIsDone();
                    }
                });
            }
        });
    }

    void getAllBooksForOneList(int id){
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                BookListRelationship r =  db.getDao().getAllBooksForOneListID(id);
                mainLooperHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.booksForOneList(r);
                    }
                });
            }
        });
    }


}
