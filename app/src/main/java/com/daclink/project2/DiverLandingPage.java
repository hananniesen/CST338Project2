package com.daclink.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.daclink.project2.database.DiveLogRepository;
import com.daclink.project2.databinding.ActivityDiverLandingPageBinding;
import com.daclink.project2.database.entities.User;

public class DiverLandingPage extends AppCompatActivity {

    private ActivityDiverLandingPageBinding binding;
    private DiveLogRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDiverLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.viewLogsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.logDiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    static Intent diverLandingPageIntentFactory(Context context) {
        return new Intent(context, DiverLandingPage.class);
    }
}