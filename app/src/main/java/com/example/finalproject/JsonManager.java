package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class JsonManager {

    public Map<String, ArrayList<BookInfo>> fromJsonToBookInfoMap(String json) {
        Map<String, ArrayList<BookInfo>> bookInfoMap = new HashMap<>();
        int targetSize = 5;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject resultsObject = jsonObject.getJSONObject("results");
            JSONArray listsArray = resultsObject.getJSONArray("lists");

            int arrayLength = listsArray.length();
            Random random = new Random();

            while (bookInfoMap.size() < targetSize) {
                int randomIndex = random.nextInt(arrayLength);

//            int arrayLength = listsArray.length();
//
//
//            Random random = new Random();
//
//                for (int iteration = 0; iteration <= 5; iteration++) {
//
//                int randomIndex = random.nextInt(arrayLength);

                JSONObject currentList = listsArray.getJSONObject(randomIndex);

                String listName = currentList.getString("list_name");

                JSONArray booksArray = currentList.getJSONArray("books");

                ArrayList<BookInfo> bookInfoList = new ArrayList<>();

                for (int i = 0; i < booksArray.length(); i++) {
                    JSONObject bookObject = booksArray.getJSONObject(i);
                    BookInfo bookInfo = new BookInfo();

                    // Extract information for each book
                    bookInfo.setAuthor(bookObject.optString("author", ""));
                    bookInfo.setTitle(bookObject.optString("title", ""));
                    bookInfo.setBookicon(bookObject.optString("book_image", ""));
                    bookInfo.setAward(String.valueOf(bookObject.optInt("rank")));
                    bookInfo.setDescription(bookObject.optString("description", ""));

                    bookInfo.setBooklistname(listName);

                    // Add the BookInfo object to the list
                    bookInfoList.add(bookInfo);
                }

                // Add the list to the map with the list name as the key
                bookInfoMap.put(listName, bookInfoList);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bookInfoMap;
    }



    ArrayList<Books> fromJsonToSearchBooksList(String json) {
        ArrayList<Books> searchBooksList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            int totalItems = jsonObject.optInt("totalItems", 0);

            // Check if totalItems is greater than 0
            if (totalItems > 0) {

                JSONArray itemsArray = jsonObject.getJSONArray("items");

                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject searchBookObject = itemsArray.getJSONObject(i);
                    Books bookSearch = new Books();

                    JSONObject volumeInfo = searchBookObject.getJSONObject("volumeInfo");
                    // JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                    if (volumeInfo.has("authors")) {
                        JSONArray authorsArray = volumeInfo.optJSONArray("authors");
                        bookSearch.setAuthor(authorsArray != null ? authorsArray.optString(0, "") : "");
                    } else {
                        bookSearch.setAuthor("");
                    }
                    // bookSearch.setAuthor(authorsArray.getString(0));
                    bookSearch.setTitle(volumeInfo.optString("title", ""));

                    bookSearch.setDescription(volumeInfo.optString("description", ""));
                    bookSearch.setPublishedDate(volumeInfo.optString("publishedDate", ""));


                    if (volumeInfo.has("imageLinks")) {
                        JSONObject imageLinks = volumeInfo.optJSONObject("imageLinks");

                        bookSearch.setBookImagelink(imageLinks != null ? imageLinks.optString("thumbnail", "") : "");
                    } else {
                        bookSearch.setBookImagelink("");
                    }

                    if (searchBookObject.has("searchInfo")) {
                        JSONObject searchInfo = searchBookObject.optJSONObject("searchInfo");
                        bookSearch.setTextSnippet(searchInfo != null ? searchInfo.optString("textSnippet", "") : "");
                    } else {
                        bookSearch.setTextSnippet("");
                    }

                    searchBooksList.add(bookSearch);

                }
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return searchBooksList;

}}
