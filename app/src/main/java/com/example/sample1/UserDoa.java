package com.example.sample1;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDoa {

    @Insert
    void insertAll(User users);

    @Query("SELECT * FROM User")
    List<User> getallusers();
}
