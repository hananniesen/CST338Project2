package com.daclink.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.daclink.project2.database.DiveLogDatabase;
import com.daclink.project2.database.DiveLogRepository;
import com.daclink.project2.database.UserDAO;
import com.daclink.project2.database.entities.User;
import com.daclink.project2.databinding.ActivityCreateAccountBinding;

public class CreateAccountActivity extends AppCompatActivity {

    private ActivityCreateAccountBinding binding;

    private DiveLogRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = DiveLogRepository.getRepository(getApplication());

        binding.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyNewAccount();
            }
        });

        binding.alreadyHaveAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LoginActivity.loginIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void verifyNewAccount() {
        String username = binding.userNameCreateEditText.getText().toString();

        if (username.isEmpty()) {
            toastMaker("Username should not be blank.");
            return;
        }

        LiveData<User> userObserver = repository.getUserByUserName(username);


        // TODO: Finish Create Account parameters (Still need to check for password meeting requirements)
        // Error of not creating an account and user.getId() error of an id not existing to start MainActivity


        userObserver.observe(this, user -> {
            if (user != null) {
                toastMaker(String.format("%s already exists. Choose a new username.", username));
                binding.userNameCreateEditText.setSelection(0);
            } else {
                String password = binding.passwordCreateEditText.getText().toString();
                String repeatPassword = binding.repeatPasswordCreateEditText.getText().toString();
                if (! password.equals(repeatPassword)) {
                    toastMaker("Passwords do not match");
                    binding.passwordCreateEditText.setSelection(0);
                    binding.repeatPasswordCreateEditText.setSelection(0);
                } else if (password doesn't meet requirements) {

                } else if (password.equals(repeatPassword) && password meets requirements) {
                    DiveLogDatabase.createUser(username, password);
                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));
                } else {
                    toastMaker("Invalid passwords");
                    binding.passwordCreateEditText.setSelection(0);
                    binding.repeatPasswordCreateEditText.setSelection(0);
                }
            }
        });


    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public static Intent createAccountIntentFactory(Context applicationContext) {
        return new Intent(applicationContext, CreateAccountActivity.class);
    }
}