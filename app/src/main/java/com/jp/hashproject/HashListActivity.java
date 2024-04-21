package com.jp.hashproject;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.ListView;

import com.jp.hashproject.adapter.ListAdapter;
import com.jp.hashproject.model.AppDataBase;
import com.jp.hashproject.model.Hash;

import java.util.List;

public class HashListActivity extends AppCompatActivity {
    AppDataBase appDataBase;
    List<Hash> hashList;
    private ListView listView;
    private ListAdapter listAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_list);

        //Database initialization
        appDataBase = Room.databaseBuilder(
                        this,
                        AppDataBase.class,
                        "dbProject"
                ).allowMainThreadQueries().
                build();

        hashList = appDataBase.hashDao().getAll();

        if(hashList != null){
            listView =findViewById(R.id.lvHash);
            listAdapter = new ListAdapter(this, R.layout.item_row, hashList);

            listView.setAdapter(listAdapter);
        }

    }

    public void openFile(View view){
        Intent openFile = new Intent(this, GetFileActivity.class);
        startActivity(openFile);
    }
}