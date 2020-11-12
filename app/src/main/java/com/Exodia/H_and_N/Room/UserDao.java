package com.Exodia.H_and_N.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.Exodia.H_and_N.entity.User;

@Dao
public interface UserDao {
    @Insert void insertOne(User user);
    @Query("SELECT * FROM user_table")
    User getAll();
    @Query("DELETE FROM user_table")
    public void nukeTable();
}
