package com.jp.hashproject;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jp.hashproject.model.Hash;
import com.jp.hashproject.model.User;

public class HashDetailsActivity extends AppCompatActivity {
    TextView tvUserId, tvFileName, tvDate, tvFilePath, tvHash;
    User user;
    Hash hash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hash_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if(getIntent().hasExtra("user")){
            user = (User) getIntent().getExtras().get("user");
            System.out.println(user);
        }
        if(getIntent().hasExtra("hash")){
            hash = (Hash) getIntent().getExtras().get("hash");

            this.tvUserId = findViewById(R.id.tvDetailUserId);
            this.tvFileName = findViewById(R.id.tvDetailFileName);
            this.tvDate = findViewById(R.id.tvDetailDate);
            this.tvFilePath = findViewById(R.id.tvDetailFilePath);
            this.tvHash = findViewById(R.id.tvDetailHash);

            this.tvUserId.setText(String.valueOf(hash.userId));
            this.tvFileName.setText(hash.fileName);
            this.tvDate.setText(hash.date);
            this.tvFilePath.setText(hash.filePath);
            this.tvHash.setText(hash.hash);
        }




    }
}