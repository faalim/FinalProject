package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TopListActivity extends AppCompatActivity
        implements
        NetworkingManager.NetworkingInterfaceListener,
        TopListAdapter.IndvBookClickListener
{

    TextView firstTitle, secTitle, thirdTitle,fourthTitle, fifthTitle;
    ArrayList<BookInfo> bookInfoData;
    NetworkingManager networkingManger;
    Button toMyList;
    JsonManager jsonManager;

    RecyclerView individualBookRecyclerView1, individualBookRecyclerView2, individualBookRecyclerView3, individualBookRecyclerView4, individualBookRecyclerView5;
    TopListAdapter adapter1,adapter2,adapter3,adapter4,adapter5;
    List<String> keyNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_recylerview);
        this.setTitle("Top 5 Weekly Best Sellers Lists");
        jsonManager = new JsonManager();
        bookInfoData = ((MyApp)getApplication()).bookInfosList;

        networkingManger = ((MyApp) getApplication()).networkingManager;
        networkingManger.listener = this;
        networkingManger.getbookinfo();

        toMyList = findViewById(R.id.toMyLists);
        toMyList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TopListActivity.this, MainActivity.class);
                startActivity(i);

            }
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void networkingFinishWithJsonString(String json) {
        JsonManager jsonManager = new JsonManager();
        Map<String, ArrayList<BookInfo>> bookInfoMap = jsonManager.fromJsonToBookInfoMap(json);

        if (bookInfoMap != null && !bookInfoMap.isEmpty()) {
            ((MyApp) getApplication()).bookInfoMap.clear();
            ((MyApp) getApplication()).bookInfoMap.putAll(bookInfoMap);

            keyNames = new ArrayList<> (bookInfoMap.keySet());


            ArrayList<BookInfo> firstList = bookInfoMap.get(keyNames.get(0));
            firstTitle = findViewById(R.id.firstTitle);
            firstTitle.setText("Top 5 Weekly: " + keyNames.get(0));

            ArrayList<BookInfo> secList = bookInfoMap.get(keyNames.get(1));
            secTitle = findViewById(R.id.secTitle);
            secTitle.setText("Top 5 Weekly: " + keyNames.get(1));

            ArrayList<BookInfo> thirdList = bookInfoMap.get(keyNames.get(2));
            thirdTitle = findViewById(R.id.thirdTitle);
            thirdTitle.setText("Top 5 Weekly: " + keyNames.get(2));

            ArrayList<BookInfo> fourthList = bookInfoMap.get(keyNames.get(3));
            fourthTitle = findViewById(R.id.fourthTitle);
            fourthTitle.setText("Top 5 Weekly: " + keyNames.get(3));

            ArrayList<BookInfo> fifthList = bookInfoMap.get(keyNames.get(4));
            fifthTitle = findViewById(R.id.fifthTitle);
            fifthTitle.setText("Top 5 Weekly: " + keyNames.get(4));

            if (firstList != null) {
                adapter1 = new TopListAdapter(firstList, this);

                individualBookRecyclerView1 = findViewById(R.id.recyclerList1);
                individualBookRecyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                individualBookRecyclerView1.setNestedScrollingEnabled(false);
                individualBookRecyclerView1.setAdapter(adapter1);
                adapter1.listener = this;
            }
            if (secList != null) {
                adapter2 = new TopListAdapter(secList, this);

                individualBookRecyclerView2 = findViewById(R.id.recyclerList2);
                individualBookRecyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                individualBookRecyclerView2.setNestedScrollingEnabled(false);
                individualBookRecyclerView2.setAdapter(adapter2);
                adapter2.listener = this;
            }
            if (thirdList != null) {
                adapter3 = new TopListAdapter(thirdList, this);

                individualBookRecyclerView3 = findViewById(R.id.recyclerList3);
                individualBookRecyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                individualBookRecyclerView3.setNestedScrollingEnabled(false);
                individualBookRecyclerView3.setAdapter(adapter3);
                adapter3.listener = this;
            }
            if (fourthList != null) {
                adapter4 = new TopListAdapter(fourthList, this);

                individualBookRecyclerView4 = findViewById(R.id.recyclerList4);
                individualBookRecyclerView4.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                individualBookRecyclerView4.setNestedScrollingEnabled(false);
                individualBookRecyclerView4.setAdapter(adapter4);
                adapter4.listener = this;
            }
            if (fifthList != null) {
                adapter5 = new TopListAdapter(fifthList, this);

                individualBookRecyclerView5 = findViewById(R.id.recyclerList5);
                individualBookRecyclerView5.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
                individualBookRecyclerView5.setNestedScrollingEnabled(false);
                individualBookRecyclerView5.setAdapter(adapter5);
                adapter5.listener = this;
            }

        }
    }



    @Override
    public void networkingFinishWithBitMapImage(Bitmap bitmap) {}

    @Override
    public void bookSelected(BookInfo selectedBook) {
        Bundle args = new Bundle();
        args.putParcelable("selectedBook", selectedBook);
        SelectedBookFragment fragment = new SelectedBookFragment();
        fragment.setArguments(args);
        fragment.show(getSupportFragmentManager(), "SelectedBookFragment");

    }


}