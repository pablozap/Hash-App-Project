package com.jp.hashproject;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jp.hashproject.adapter.ListAdapter;
import com.jp.hashproject.model.AppDataBase;
import com.jp.hashproject.model.Hash;
import com.jp.hashproject.model.User;

import java.util.List;

public class HashListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    AppDataBase appDataBase;
    List<Hash> hashList;
    private ListView listView;
    private ListAdapter listAdapter;
    User user;

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

        if(getIntent().hasExtra("user")){
            user = (User) getIntent().getExtras().get("user");
            System.out.println(user);
        }

        hashList = appDataBase.hashDao().getAll();

        if(hashList != null){
            listView =findViewById(R.id.lvHash);
            listView.setOnItemClickListener(this);
            listAdapter = new ListAdapter(this, R.layout.item_row, hashList);

            listView.setAdapter(listAdapter);
        }

    }

    public void openFile(View view){
        Intent openFile = new Intent(this, GetFileActivity.class);
        openFile.putExtra("user", user);
        startActivity(openFile);
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l){
        Toast.makeText(this, "Elemento cliqueado", Toast.LENGTH_SHORT).show();
        Intent hashDetails = new Intent(this, HashDetailsActivity.class);

        hashDetails.putExtra("hash", listAdapter.getItem(position));
        hashDetails.putExtra("user", user);
        startActivity(hashDetails);
    }
}