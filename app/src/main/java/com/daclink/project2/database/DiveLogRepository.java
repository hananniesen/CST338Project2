package com.daclink.project2.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.daclink.project2.MainActivity;
import com.daclink.project2.database.entities.DiveLog;
import com.daclink.project2.database.entities.User;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class DiveLogRepository {
    private final DiveLogDAO diveLogDAO;
    private final UserDAO userDAO;
    private ArrayList<DiveLog> allLogs;

    private static DiveLogRepository repository;

    private DiveLogRepository(Application application) {
        DiveLogDatabase db = DiveLogDatabase.getDatabase(application);
        this.diveLogDAO = db.diveLogDAO();
        this.userDAO = db.userDAO();
        this.allLogs = (ArrayList<DiveLog>) this.diveLogDAO.getAllRecords();
    }

    public static DiveLogRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<DiveLogRepository> future = DiveLogDatabase.databaseWriteExecutor.submit(
                new Callable<DiveLogRepository>() {
                    @Override
                    public DiveLogRepository call() throws Exception {
                        return new DiveLogRepository(application);
                    }
                }
        );
        try{
            return future.get();
        }catch (InterruptedException | ExecutionException e) {
            Log.d(MainActivity.TAG, "Problem getting GymLogRepository, thread error.");
        }
        return  null;
    }



    // getAllLog explained in start of video 04
    public ArrayList<DiveLog> getAllLogs() {
        Future<ArrayList<DiveLog>> future = DiveLogDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<DiveLog>>() {
                    @Override
                    public ArrayList<DiveLog> call() throws Exception {
                        return (ArrayList<DiveLog>) diveLogDAO.getAllRecords();
                    }
                }
        );
        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem when getting all GymLogs in the repository");
        }
        return null;
    }

    public void insertDiveLog(DiveLog diveLog) {
        DiveLogDatabase.databaseWriteExecutor.execute(()-> {
                    diveLogDAO.insert(diveLog);
                });
    }

    public void insertUser(User... user) {
        DiveLogDatabase.databaseWriteExecutor.execute(()->
        {
            userDAO.insert(user);
        });
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }

    public ArrayList<DiveLog> getAllLogsByUserId(int loggedInUserId) {
        Future<ArrayList<DiveLog>> future = DiveLogDatabase.databaseWriteExecutor.submit(
                new Callable<ArrayList<DiveLog>>() {
                    @Override
                    public ArrayList<DiveLog> call() throws Exception {
                        return (ArrayList<DiveLog>) diveLogDAO.getRecordsByUserId(loggedInUserId);
                    }
                }
        );
        try{
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.i(MainActivity.TAG, "Problem when getting all GymLogs in the repository");
        }
        return null;
    }
}
