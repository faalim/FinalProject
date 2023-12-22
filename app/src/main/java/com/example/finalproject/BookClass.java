package com.example.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BookClass implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    int bookID;
   // int year;
    String title;
    String author;
    String publishedDate;
    String description;
    String bookImagelink;



    int list_bookID;

    public BookClass(){}
    protected BookClass(Parcel in) {
        bookID = in.readInt();
        title = in.readString();
        author = in.readString();
        publishedDate = in.readString();
        description = in.readString();
        bookImagelink = in.readString();
        list_bookID = in.readInt();
    }

    public static final Creator<BookClass> CREATOR = new Creator<BookClass>() {
        @Override
        public BookClass createFromParcel(Parcel in) {
            return new BookClass(in);
        }

        @Override
        public BookClass[] newArray(int size) {
            return new BookClass[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(bookID);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(publishedDate);
        dest.writeString(description);
        dest.writeString(bookImagelink);
        dest.writeInt(list_bookID);
    }
}
