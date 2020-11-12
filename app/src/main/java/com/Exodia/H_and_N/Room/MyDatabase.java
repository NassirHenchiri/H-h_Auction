package com.Exodia.H_and_N.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.Exodia.H_and_N.entity.User;

@Database(entities = {User.class}, version = 2, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    private static MyDatabase instance;

    public abstract UserDao userDao();

    public static MyDatabase getMyDatabase(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), MyDatabase.class, "recap_app_all_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

}
