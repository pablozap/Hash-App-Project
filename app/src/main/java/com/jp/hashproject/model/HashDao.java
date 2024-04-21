package com.jp.hashproject.model;

import androidx.room.*;

import java.util.List;

@Dao
public interface HashDao {
    @Query("SELECT * FROM Hash")
    List<Hash> getAll();
    @Insert
    void insert(Hash... hashes);
    @Query("SELECT * FROM Hash WHERE id = :id")
    Hash findById(int id);
    @Update
    void update(Hash hash);
    @Delete
    void delete(Hash hash);
}
