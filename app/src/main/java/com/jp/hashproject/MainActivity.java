package com.jp.hashproject;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view){
    }

    public void signUp(View view){
        //TODO Create a new window for Sign Up, with just name, email, and password for the new user
    }
}