package com.daclink.project2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.daclink.project2.database.entities.InstructorsClass;

import java.util.List;

@Dao
public interface InstructorsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(InstructorsClass instructorsClass);

    @Delete
    void delete(InstructorsClass instructorsClass);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(InstructorsClass instructorsClass);

    @Query("SELECT * FROM " + DiveLogDatabase.INSTRUCTORS_TABLE + " ORDER BY instructorId")
    LiveData<List<InstructorsClass>> getAllInstructors();

    @Query("DELETE FROM " + DiveLogDatabase.INSTRUCTORS_TABLE)
    void deleteAll();

    @Query("SELECT studentsId FROM " + DiveLogDatabase.INSTRUCTORS_TABLE + " WHERE instructorName == :instructorName")
    String getInstructorClassStudents(String instructorName);

    @Query("SELECT * FROM " + DiveLogDatabase.INSTRUCTORS_TABLE + " WHERE instructorId == :instructorId")
    LiveData<InstructorsClass> getInstructorByInstructorId(int instructorId);

    @Query("SELECT * FROM " + DiveLogDatabase.INSTRUCTORS_TABLE + " WHERE instructorName == :instructorName")
    LiveData<InstructorsClass> getInstructorByInstructorName(String instructorName);
}
