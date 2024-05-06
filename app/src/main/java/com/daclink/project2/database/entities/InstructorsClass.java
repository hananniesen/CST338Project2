package com.daclink.project2.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.daclink.project2.database.DiveLogDatabase;

import java.util.Objects;

@Entity(tableName = DiveLogDatabase.INSTRUCTORS_TABLE)
public class InstructorsClass {

    @PrimaryKey(autoGenerate = true)
    private int instructorId;
    private String instructorName;
    private String studentsId;

    public InstructorsClass(String instructorName) {
        this.instructorName = instructorName;
        this.studentsId = "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstructorsClass that = (InstructorsClass) o;
        return instructorId == that.instructorId && Objects.equals(instructorName, that.instructorName) && Objects.equals(studentsId, that.studentsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instructorId, instructorName, studentsId);
    }

    public int getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(int instructorId) {
        this.instructorId = instructorId;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getStudentsId() {
        return studentsId;
    }

    public void setStudentsId(String studentsId) {
        this.studentsId = studentsId;
    }
}
