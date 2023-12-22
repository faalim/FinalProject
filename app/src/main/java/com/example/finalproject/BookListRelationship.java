package com.example.finalproject;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class BookListRelationship {


    @Embedded
    ListClass list;// one - many relationship

    @Relation(
            parentColumn = "listID",
            entityColumn = "list_bookID"
    )
    List<BookClass> allBooks;

}
