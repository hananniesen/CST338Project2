package com.daclink.project2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.daclink.project2.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + DiveLogDatabase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getAllUsers();

    @Query("DELETE from " + DiveLogDatabase.USER_TABLE)
    void deleteAll();

    @Query("SELECT * from " + DiveLogDatabase.USER_TABLE + " WHERE username == :username" )
    LiveData<User> getUserByUserName(String username);

    @Query("SELECT * from " + DiveLogDatabase.USER_TABLE + " WHERE id == :userId" )
    LiveData<User> getUserByUserId(int userId);
}
