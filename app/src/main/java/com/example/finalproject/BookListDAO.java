package com.example.finalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface BookListDAO {
    @Query("select * from ListClass")
    List<ListClass> getAllLists();

    @Insert
    void addNewList(ListClass list);

    @Insert
    void addNewBookForOneList(BookClass book);
    @Delete
    void deleteOneList(ListClass l);
    @Delete
    void deleteOneBook(BookClass c);

    @Query("delete from BookClass where list_bookID = :listID")
    void deleteBooksForList(int listID);



    @Transaction
    @Query("select * from ListClass o, BookClass c where o.listID = :listID and o.listID = c.list_bookID ")
    BookListRelationship getAllBooksForOneListID(int listID);








}
