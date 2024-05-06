package com.daclink.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.daclink.project2.database.DiveHubRepository;
import com.daclink.project2.database.entities.User;
import com.daclink.project2.databinding.ActivityCreateAccountBinding;

public class CreateAccountActivity extends AppCompatActivity {

    private ActivityCreateAccountBinding binding;

    private DiveHubRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = DiveHubRepository.getRepository(getApplication());

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

        // not enough time for password requirements

        userObserver.observe(this, user -> {
            if (user != null) {
                userObserver.removeObservers(this);
                toastMaker(String.format("%s is already taken.", username));
                binding.userNameCreateEditText.setSelection(0);
            } else {
                String password = binding.passwordCreateEditText.getText().toString();
                String repeatPassword = binding.repeatPasswordCreateEditText.getText().toString();
                if (! password.equals(repeatPassword)) {
                    toastMaker("Passwords do not match");
                    binding.passwordCreateEditText.setSelection(0);
                    binding.repeatPasswordCreateEditText.setSelection(0);
                } else if (password.equals(repeatPassword)) {
                    User newUser = new User(username, password);
                    repository.insertUser(newUser);
                    startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
                    toastMaker("Account successfully created!");
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