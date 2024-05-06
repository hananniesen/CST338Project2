package com.daclink.project2.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.daclink.project2.database.DiveHubRepository;
import com.daclink.project2.database.entities.DiveLog;

import java.util.List;

public class DiveLogViewModel extends AndroidViewModel {
    private final DiveHubRepository repository;

//    private final LiveData<List<DiveLog>> allLogsById;

    public DiveLogViewModel (Application application) {
        super(application);
        repository = DiveHubRepository.getRepository(application);
//        allLogsById = repository.getAllLogsByUserIdLiveData(userId);
    }


    public LiveData<List<DiveLog>> getAllLogsById(int userId) {
        return repository.getAllLogsByUserIdLiveData(userId);
    }

    public void insert(DiveLog log) {
        repository.insertDiveLog(log);
    }
}
