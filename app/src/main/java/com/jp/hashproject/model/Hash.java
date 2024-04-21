package com.jp.hashproject.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Hash {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String fileName;
    public String filePath;
    public String date;
    public String hash;


    public Hash(String fileName, String filePath, String date, String hash) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.date = date;
        this.hash = hash;
    }

    // Método toString para representación textual
    @NonNull
    @Override
    public String toString() {
        return "Archivo{" +
                "\nid=" + id +
                "\nnombreArchivo='" + fileName + '\'' +
                "\nubicacion='" + filePath + '\'' +
                "\nfechaHora=" + date +
                "\nhash='" + hash + '\'' +
                '}';
    }
}