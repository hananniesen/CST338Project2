package com.daclink.project2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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