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

public class SignUpActivity extends AppCompatActivity {

    AppDataBase appDataBase;
    EditText txtName, txtEmail, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);

        //Database initialization
        appDataBase = Room.databaseBuilder(
                        this,
                        AppDataBase.class,
                        "dbProject"
                ).allowMainThreadQueries().
                build();
    }

    public void signUp(View view) {
        String name = txtName.getText().toString(),
                email = txtEmail.getText().toString(),
                password = txtPassword.getText().toString();
        //Verify if email is already and database, signUp the user if it´s not and change to Log In Activity
        try {
            Boolean verify = appDataBase.userDao().verifyExistingEmail(email);
            if (!verify) {
                User user = new User(name, email, password);
                appDataBase.userDao().insert(user);
                Log.i("Created", "User created successfully");
                login(view);
            } else {
                Log.i("Failed", "User created failed");
            }

        } catch (Exception error) {
            System.out.println("Unexpected error:");
        }
    }

    public void login(View view) {
        Intent loginWindow = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(loginWindow);
    }
}