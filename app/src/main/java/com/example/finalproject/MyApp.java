package com.example.finalproject;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApp extends Application {
    ArrayList<BookInfo> bookInfosList = new ArrayList<>(0);
    ArrayList<Books> bookSearch_list = new ArrayList<>(0);

    Map<String, ArrayList<BookInfo>> bookInfoMap = new HashMap<>();
    NetworkingManager networkingManager = new NetworkingManager();
    static ExecutorService executorService = Executors.newFixedThreadPool(4);
    static Handler mainhandler = new Handler(Looper.getMainLooper());
    DatabaseManager dbm = new DatabaseManager();

}
