package com.daclink.project2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daclink.project2.database.DiveHubRepository;
import com.daclink.project2.database.entities.DiveLog;
import com.daclink.project2.database.entities.User;
import com.daclink.project2.databinding.ActivityLogDiveBinding;
import com.daclink.project2.viewHolders.DiveLogAdapter;
import com.daclink.project2.viewHolders.DiveLogViewModel;

public class LogDiveActivity extends AppCompatActivity {

    ActivityLogDiveBinding binding;
    DiveHubRepository repository;
    User user;

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

        repository = DiveHubRepository.getRepository(getApplication());

        loggedInUserId = getIntent().getIntExtra("loggedInUserId", -1);

        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user = user;
            if (this.user != null) {
                invalidateOptionsMenu();
            }
        });

        binding.logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInformationFromDisplay();
                insertDiveLogRecord();
            }
        });

        binding.viewLogsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogDiveActivity.this, ViewLogsActivity.class);
                intent.putExtra("loggedInUserId", loggedInUserId);
                startActivity(intent);
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
        toastMaker("Dive Log Added");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.logoutMenuItem);
        item.setVisible(true);
        if (user == null) {
            return false;
        }
        item.setTitle(user.getUsername());
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), loggedInUserId));
                return false;
            }
        });
        return true;
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}