package com.example.sample1;

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface UserDoa {

    @Insert
    void insertAll(User users);

}
