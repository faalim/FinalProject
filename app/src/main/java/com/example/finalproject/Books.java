package com.example.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Books implements Parcelable {
    String title;
    String author;
    String publishedDate;
    String description;
    String textSnippet;
    String bookImagelink;

    public Books(String title, String author, String publishedDate, String description, String textSnippet, String bookImagelink) {
        this.title = title;
        this.author = author;
        this.publishedDate = publishedDate;
        this.description = description;
        this.textSnippet = textSnippet;
        this.bookImagelink = bookImagelink;
    }
    public Books(){}

    protected Books(Parcel in) {
        title = in.readString();
        author = in.readString();
        publishedDate = in.readString();
        description = in.readString();
        textSnippet = in.readString();
        bookImagelink = in.readString();
    }

    public static final Creator<Books> CREATOR = new Creator<Books>() {
        @Override
        public Books createFromParcel(Parcel in) {
            return new Books(in);
        }

        @Override
        public Books[] newArray(int size) {
            return new Books[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTextSnippet() {
        return textSnippet;
    }

    public void setTextSnippet(String textSnippet) {
        this.textSnippet = textSnippet;
    }

    public String getBookImagelink() {
        if (bookImagelink != null && bookImagelink.startsWith("http://")) {
            return bookImagelink.replace("http://", "https://");
        }
        return bookImagelink;
    }

    public void setBookImagelink(String bookImagelink) {
        this.bookImagelink = bookImagelink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(publishedDate);
        dest.writeString(description);
        dest.writeString(textSnippet);
        dest.writeString(bookImagelink);
    }
}
