package com.daclink.project2.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.daclink.project2.database.entities.DiveLog;

import java.util.List;

@Dao
public interface DiveLogDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(DiveLog diveLog);

    @Query("SELECT * FROM " + DiveLogDatabase.DIVE_LOG_TABLE + " ORDER BY date DESC")
    List<DiveLog> getAllRecords();

    @Query("SELECT * FROM " + DiveLogDatabase.DIVE_LOG_TABLE + " WHERE userId = :loggedInUserId ORDER BY date DESC")
    List<DiveLog> getRecordsByUserId(int loggedInUserId);
}
