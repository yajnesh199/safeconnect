package com.example.sample1;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class database extends RoomDatabase {
    public abstract UserDoa userDao();
}
