package com.example.finalproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(version = 1, entities = {BookClass.class, ListClass.class})
public abstract class BookListDatabase extends RoomDatabase {
    public abstract BookListDAO getDao();
}
