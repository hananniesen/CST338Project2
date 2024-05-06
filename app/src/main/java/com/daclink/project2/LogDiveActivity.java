package com.daclink.project2;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.daclink.project2.database.DiveHubRepository;
import com.daclink.project2.database.entities.DiveLog;
import com.daclink.project2.databinding.ActivityLogDiveBinding;

import java.util.ArrayList;

public class LogDiveActivity extends AppCompatActivity {

    ActivityLogDiveBinding binding;
    DiveHubRepository repository;

    String mDiveType = "";
    String mTimeSpent = "";
    double mMaxDepth = 0.0;
    String mAdditionalComments = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogDiveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        binding.logDisplayTextView.setMovementMethod(new ScrollingMovementMethod());
//        updateDisplay();
//
//        binding.logButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getInformationFromDisplay();
//                insertDiveLogRecord();
//                updateDisplay();
//            }
//        });
//    }
//
//    private void insertDiveLogRecord() {
//        if (mDiveType.isEmpty()) {
//            return;
//        }
//        if (mTimeSpent.isEmpty()) {
//            return;
//        }
//        DiveLog log = new DiveLog(mDiveType, mTimeSpent, mMaxDepth, mAdditionalComments, loggedInUserId);
//        repository.insertDiveLog(log);
//    }
//
//    private void updateDisplay() {
//        ArrayList<DiveLog> allLogs = repository.getAllLogsByUserId(loggedInUserId);
//        if (allLogs.isEmpty()) {
//            binding.logDisplayTextView.setText(R.string.nothing_to_show);
//        }
//        StringBuilder sb = new StringBuilder();
//        for (DiveLog log : allLogs) {
//            sb.append(log);
//        }
//        binding.logDisplayTextView.setText(sb.toString());
//    }
//
//    private void getInformationFromDisplay() {
//        mDiveType = binding.diveTypeInputEditText.getText().toString();
//
//        mTimeSpent = binding.timeInputEditText.getText().toString();
//
//        try {
//            mMaxDepth = Double.parseDouble(binding.maxDepthInputEditText.getText().toString());
//        } catch (NumberFormatException e) {
////            Log.d(TAG, "Error reading value from max depth edit text.");
//        }
//
//        mAdditionalComments = binding.addiCommInputEditText.getText().toString();
//    }
}