package com.daclink.project2.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.daclink.project2.MainActivity;
import com.daclink.project2.database.entities.DiveLog;
import com.daclink.project2.database.entities.InstructorsClass;
import com.daclink.project2.database.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class DiveHubRepository {
    private final DiveLogDAO diveLogDAO;
    private final UserDAO userDAO;
    private final InstructorsDAO instructorsDAO;
    private ArrayList<DiveLog> allLogs;

    private static DiveHubRepository repository;

    private DiveHubRepository(Application application) {
        DiveLogDatabase db = DiveLogDatabase.getDatabase(application);
        this.diveLogDAO = db.diveLogDAO();
        this.userDAO = db.userDAO();
        this.instructorsDAO = db.instructorsDAO();
        this.allLogs = (ArrayList<DiveLog>) this.diveLogDAO.getAllRecords();
    }

    public static DiveHubRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<DiveHubRepository> future = DiveLogDatabase.databaseWriteExecutor.submit(
                new Callable<DiveHubRepository>() {
                    @Override
                    public DiveHubRepository call() throws Exception {
                        return new DiveHubRepository(application);
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

    public void deleteUser(User user) {
        DiveLogDatabase.databaseWriteExecutor.execute(()->
        {
            userDAO.delete(user);
        });
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }

    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }


    // TODO: Unfinished
//    public void insertUserToInstructorClass(InstructorsClass instructor) {
//        DiveLogDatabase.databaseWriteExecutor.execute(() ->
//                {
//                    instructor.insertUser(user);
//                }
//                );
//    }

    public void deleteInstructorClass(InstructorsClass instructor) {
        DiveLogDatabase.databaseWriteExecutor.execute(() ->
                {
                    instructorsDAO.delete(instructor);
                }
        );
    }

    public LiveData<InstructorsClass> getInstructorByInstructorId(int id) {
        return instructorsDAO.getInstructorByInstructorId(id);
    }


    // TODO: Unfinished
//    public String getInstructorClassStudents(int loggedInUserId) {
//        return instructorsDAO.getInstructorClassStudents(loggedInUserId);
//    }

    public LiveData<List<DiveLog>> getAllLogsByUserIdLiveData(int loggedInUserId) {
        return diveLogDAO.getRecordsByUserIdLiveData(loggedInUserId);
    }

    public void updateUser(User user) {
        DiveLogDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.update(user);
        });
    }
}
