package com.daclink.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.daclink.project2.database.DiveHubRepository;
import com.daclink.project2.database.entities.User;
import com.daclink.project2.databinding.ActivityDiverLandingPageBinding;

public class DiverLandingPage extends AppCompatActivity {

    private ActivityDiverLandingPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDiverLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.landingLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
            }
        });

        binding.landingSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(CreateAccountActivity.createAccountIntentFactory(getApplicationContext()));
            }
        });
    }

    static Intent diverLandingPageIntentFactory(Context context) {
        return new Intent(context, DiverLandingPage.class);
    }
}