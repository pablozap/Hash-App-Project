package com.jp.hashproject.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.nio.file.SecureDirectoryStream;

@Entity(foreignKeys = {@ForeignKey(entity = User.class,
parentColumns = "id",
childColumns = "userId")})
public class Hash implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int userId;
    public String fileName;
    public String filePath;
    public String date;
    public String hash;


    public Hash(int userId , String fileName, String filePath, String date, String hash) {
        this.userId = userId;
        this.fileName = fileName;
        this.filePath = filePath;
        this.date = date;
        this.hash = hash;
    }

    // Método toString para representación textual

    @Override
    public String toString() {
        return "Hash{" +
                "userId=" + userId +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", date='" + date + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}