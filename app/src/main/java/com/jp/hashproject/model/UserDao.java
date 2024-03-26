package com.jp.hashproject.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("Select * from User")
    List<User> getAll();
    @Query(" Select exists (select email from User where email = :email)")
    Boolean verifyExistingEmail(String email);
    @Query("Select * from User where email = :email and password = :password")
    User login(String email, String password);
    @Query("Delete from User")
    void delete();
    @Insert
    void insert(User... users);
}
