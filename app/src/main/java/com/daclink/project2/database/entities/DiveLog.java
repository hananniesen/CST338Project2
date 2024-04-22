package com.daclink.project2.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.daclink.project2.database.DiveLogDatabase;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(tableName = DiveLogDatabase.DIVE_LOG_TABLE)
public class DiveLog {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String exercise;
    private double weight;
    private int reps;
    private LocalDateTime date;
    private int userId;

//    Dive log data -> TODO: Replace values
//    private String typeOfDive;
//    private LocalDateTime date;
//    private double maxDepth;
//    private String additionalCom;


    public DiveLog(String exercise, double weight, int reps, int userId) {
        this.exercise = exercise;
        this.weight = weight;
        this.reps = reps;
        this.userId = userId;
        date = LocalDateTime.now();
    }

    @NonNull
    @Override
    public String toString() {
        return exercise + '\n' +
                "weight: " + weight + '\n' +
                "reps: " + reps + '\n' +
                "date: " + date.toString() + '\n' +
                "=-=-=-=-=-=-=\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiveLog diveLog = (DiveLog) o;
        return id == diveLog.id && Double.compare(weight, diveLog.weight) == 0 && reps == diveLog.reps && userId == diveLog.userId && Objects.equals(exercise, diveLog.exercise) && Objects.equals(date, diveLog.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, exercise, weight, reps, date, userId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
