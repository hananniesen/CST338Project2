package com.daclink.project2;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daclink.project2.database.DiveHubRepository;
import com.daclink.project2.database.entities.DiveLog;
import com.daclink.project2.databinding.ActivityLogDiveBinding;
import com.daclink.project2.viewHolders.DiveLogAdapter;
import com.daclink.project2.viewHolders.DiveLogViewModel;

import java.util.ArrayList;

public class LogDiveActivity extends AppCompatActivity {

    ActivityLogDiveBinding binding;
    DiveHubRepository repository;

    private DiveLogViewModel diveLogViewModel;

    String mDiveType = "";
    String mTimeSpent = "";
    double mMaxDepth = 0.0;
    String mAdditionalComments = "";

    public static final String TAG = "DAC_DIVELOG";

    int loggedInUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogDiveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        diveLogViewModel = new ViewModelProvider(this).get(DiveLogViewModel.class);

        RecyclerView recyclerView = binding.logDisplayRecyclerView;
        final DiveLogAdapter adapter = new DiveLogAdapter(new DiveLogAdapter.DiveLogDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repository = DiveHubRepository.getRepository(getApplication());

        loggedInUserId = getIntent().getIntExtra("loggedInUserId", -1);

        diveLogViewModel.getAllLogsById(loggedInUserId).observe(this, diveLogs -> {
            adapter.submitList(diveLogs);
        });

        binding.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                insertDiveLogRecord();
            }
        });
    }

    private void insertDiveLogRecord() {
        if (mDiveType.isEmpty()) {
            return;
        }
        if (mTimeSpent.isEmpty()) {
            return;
        }
        DiveLog log = new DiveLog(mDiveType, mTimeSpent, mMaxDepth, mAdditionalComments, loggedInUserId);
        repository.insertDiveLog(log);
        toastmaker("Dive Log Added");
    }

    @Deprecated
    private void updateDisplay() {
        ArrayList<DiveLog> allLogs = repository.getAllLogsByUserId(loggedInUserId);
        StringBuilder sb = new StringBuilder();
        for (DiveLog log : allLogs) {
            sb.append(log);
        }
    }

    private void getInformationFromDisplay() {
        mDiveType = binding.diveTypeInputEditText.getText().toString();

        mTimeSpent = binding.timeInputEditText.getText().toString();

        try {
            mMaxDepth = Double.parseDouble(binding.maxDepthInputEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from max depth edit text.");
        }

        mAdditionalComments = binding.addiCommInputEditText.getText().toString();
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}