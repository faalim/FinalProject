package com.example.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class BookInfo implements Parcelable {
    String author;
    String title;
    String award;
    String bookicon;
    String description;
    String booklistname;

    public BookInfo(){}

    public BookInfo(String author, String title, String award, String bookicon, String description, String booklistname) {
        this.author = author;
        this.title = title;
        this.award = award;
        this.bookicon = bookicon;
        this.description = description;
        this.booklistname = booklistname;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAward() {
        return award;
    }

    public void setAward(String award) {
        this.award = award;
    }

    public String getBookicon() {
        return bookicon;
    }

    public void setBookicon(String bookicon) {
        this.bookicon = bookicon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBooklistname() {
        return booklistname;
    }

    public void setBooklistname(String booklistname) {
        this.booklistname = booklistname;
    }

    protected BookInfo(Parcel in) {
        author = in.readString();
        title = in.readString();
        award = in.readString();
        bookicon = in.readString();
        description = in.readString();
        booklistname = in.readString();
    }

    public static final Creator<BookInfo> CREATOR = new Creator<BookInfo>() {
        @Override
        public BookInfo createFromParcel(Parcel in) {
            return new BookInfo(in);
        }

        @Override
        public BookInfo[] newArray(int size) {
            return new BookInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(award);
        dest.writeString(bookicon);
        dest.writeString(description);
        dest.writeString(booklistname);
    }

}
