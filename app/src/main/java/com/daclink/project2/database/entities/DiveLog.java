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
    private int userId;
    private String diveType;
    private String timeSpent;
    private double maxDepth;
    private String additionalComments;
    private LocalDateTime date;


    public DiveLog(String diveType, String timeSpent, double maxDepth, String additionalComments, int userId) {
        this.diveType = diveType;
        this.timeSpent = timeSpent;
        this.maxDepth = maxDepth;
        this.additionalComments = additionalComments;
        this.userId = userId;
        date = LocalDateTime.now();
    }

    @NonNull
    @Override
    public String toString() {
        return "Date: \n" + date.toString() + '\n' +
                "Dive Type: \n" + diveType + '\n' +
                "Time Spent at Depth: \n" + timeSpent + '\n' +
                "Max Depth: \n" + maxDepth + '\n' +
                "Additional Comments: \n" + additionalComments + '\n' +
                "=-=-=-=-=-=-=\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiveLog diveLog = (DiveLog) o;
        return id == diveLog.id && userId == diveLog.userId && Double.compare(maxDepth, diveLog.maxDepth) == 0 && Objects.equals(diveType, diveLog.diveType) && Objects.equals(date, diveLog.date) && Objects.equals(timeSpent, diveLog.timeSpent) && Objects.equals(additionalComments, diveLog.additionalComments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, diveType, date, timeSpent, maxDepth, additionalComments);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDiveType() {
        return diveType;
    }

    public void setDiveType(String diveType) {
        this.diveType = diveType;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }

    public double getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(double maxDepth) {
        this.maxDepth = maxDepth;
    }

    public String getAdditionalComments() {
        return additionalComments;
    }

    public void setAdditionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
    }
}
