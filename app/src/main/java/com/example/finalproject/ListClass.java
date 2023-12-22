package com.example.finalproject;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class ListClass {

    @PrimaryKey(autoGenerate = true)
    int listID;

    String listName;
    String listDescrip;

}
