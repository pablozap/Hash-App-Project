package com.jp.hashproject;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class HashListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hash_list);
    }

    public void openFile(View view){
        Intent openFile = new Intent(this, GetFileActivity.class);
        startActivity(openFile);
    }
}