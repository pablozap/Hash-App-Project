package com.jp.hashproject.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(
        entities = {User.class, Hash.class},
        version = 1
)
public abstract class AppDataBase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract HashDao hashDao();
}
