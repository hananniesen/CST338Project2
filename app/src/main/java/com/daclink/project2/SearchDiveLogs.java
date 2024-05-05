package com.daclink.project2;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.daclink.project2.database.DiveLogRepository;
import com.daclink.project2.databinding.ActivityMainBinding;
import com.daclink.project2.databinding.SearchDiveLogsBinding;

public class SearchDiveLogs extends AppCompatActivity {
    private SearchDiveLogsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SearchDiveLogsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}