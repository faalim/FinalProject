package com.example.finalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class NetworkingManager{
      interface NetworkingInterfaceListener{
          void networkingFinishWithJsonString(String json);
          void networkingFinishWithBitMapImage(Bitmap bitmap);
      }

      int Apitype;
      NetworkingInterfaceListener listener;

      String nyTimesApiKey = "nLk9cAx44XnaO5Tx4g03CoCIRoU2JEXe";
      String googleBooksApi = "AIzaSyCL1l-CHYM3r0rrs3Y_TYN-7vzmTMuR9RI";
    void  getbookinfo(){
        Apitype = 0;
        //Get top 5 books for all the Best Sellers lists to date.NYtimes
        String url = "https://api.nytimes.com/svc/books/v3/lists/overview.json?api-key="+nyTimesApiKey;
        connect(url);

    }
    void searchBooks(String book){
        Apitype=1;
        String url =  "https://books.googleapis.com/books/v1/volumes?q="+book+"&orderBy=relevance&key="+googleBooksApi;
        connect(url);

    }
    void getSelectedImage(String urls){
        MyApp.executorService.execute(new Runnable() {
          @Override
          public void run() {
              URL url = null;
              try {
                  url = new URL(urls);
              } catch (MalformedURLException e) {
                  throw new RuntimeException(e);
              }
              try {
                  Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                  MyApp.mainhandler.post(new Runnable() {
                      @Override
                      public void run() {
                          if (listener != null) {

                              listener.networkingFinishWithBitMapImage(image);
                          }
                      }
                  } );

              } catch (IOException e) {
                  throw new RuntimeException(e);
              }
          }
      }
        );
    }
    void downloadImage(String icon, ImageView img){
        MyApp.executorService.execute(new Runnable() {
            String iconurl = icon;
            @Override
            public void run() {
                InputStream is = null;
                try {
                    is = (InputStream) new URL(iconurl).getContent();
                    Bitmap d = BitmapFactory.decodeStream(is);
                    MyApp.mainhandler.post(new Runnable() {
                        @Override
                        public void run() {

                            img.setImageBitmap(d);

                        }
                    });
                }catch (IOException e) {
                    e.printStackTrace();
                    // Handle the exception, for example, set a default image or show an error message
//                    MyApp.mainhandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    });
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

    }
    void connect(String urls) {

            MyApp.executorService.execute(new Runnable() {
                @Override
                public void run() {

                    HttpURLConnection connection = null;

                    try {
                        URL url = new URL(urls);
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setRequestProperty("Accept", "application/json");
                        int responseCode = connection.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) {


                            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                            StringBuffer buffer = new StringBuffer();
                            int v;
                            while ((v = reader.read()) != -1) {
                                buffer.append((char) v);
                            }
                            String jsonResponse = buffer.toString();


                            MyApp.mainhandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (listener != null) {

                                            listener.networkingFinishWithJsonString(jsonResponse);

                                    }
                                }
                            });
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        System.out.println("There is an error");
                        e.printStackTrace();
                    }
                    finally {
                        if (connection != null) {
                            connection.disconnect();
                        }
                    }
                }
            });
        }

}



