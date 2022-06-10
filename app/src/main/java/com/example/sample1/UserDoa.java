package com.example.sample1;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDoa {

    @Insert
    void insertAll(User user);

    @Query("SELECT * FROM User")
    List<User> getallusers();

    @Query("SELECT * FROM User where receiver_id= :user_id OR sender_id= :user_id")
    List<User> getchat(String user_id);
}
