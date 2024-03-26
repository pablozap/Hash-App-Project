package com.jp.hashproject;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.room.Room;
import com.jp.hashproject.model.AppDataBase;
import com.jp.hashproject.model.User;

public class MainActivity extends AppCompatActivity {

    AppDataBase appDataBase = null;
    EditText txtEmail, txtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Database initialization
        appDataBase = Room.databaseBuilder(
                        this,
                        AppDataBase.class,
                        "dbProject"
                ).allowMainThreadQueries().
                build();

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

    }

    //Query to database to find User
    public void login(View view){
        String email = String.valueOf(txtEmail.getText());
        String password = String.valueOf(txtPassword.getText());
        User user = appDataBase.userDao().login(email, password);
        if(user != null){
            Log.i("Logging", "Successful");
            System.out.println(user.getName());
        }else{
            Log.i("Logging", "Failed");
        }
    }

    public void signUp(View view){
        Intent signUpWindow = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(signUpWindow);
    }
}