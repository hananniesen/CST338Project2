package com.daclink.project2;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.daclink.project2.database.DiveHubRepository;
import com.daclink.project2.database.entities.User;
import com.daclink.project2.databinding.ActivityAdminSettingsBinding;
import com.daclink.project2.databinding.ActivitySettingsBinding;

public class AdminSettingsActivity extends AppCompatActivity {

    private ActivityAdminSettingsBinding binding;
    private User user;
    private DiveHubRepository repository;

    int loggedInUserId;
    private static final String MAIN_ACTIVITY_USER_ID = "com.daclink.project2.MAIN_ACTIVITY_USER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = DiveHubRepository.getRepository(getApplication());

        loggedInUserId = getIntent().getIntExtra("loggedInUserId", -1);

        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            this.user = user;
            if (this.user != null) {
                binding.changeUsernameTextView.setText(String.format("Username: %s", user.getUsername()));
                invalidateOptionsMenu();
            }
        });

        binding.instructorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        binding.usernameEditImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUsername();
                binding.changeUsernameTextView.setText(String.format("Username: %s", user.getUsername()));
            }
        });

        binding.passwordEditImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

        binding.deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount();
            }
        });
    }

    private void deleteAccount() {
        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);
        userObserver.observe(this, user -> {
            if (user != null) {
                String password = binding.passwordDeleteAccountEditText.getText().toString();
                String repeatPassword = binding.repeatPasswordDeleteAccountEditText.getText().toString();
                if (password.equals(user.getPassword()) && password.equals(repeatPassword)) {
                    repository.deleteUser(user);
                    loggedInUserId = -1;
                    updateSharedPreference();
                    getIntent().putExtra(MAIN_ACTIVITY_USER_ID, -1);
                    startActivity(LoginActivity.loginIntentFactory(getApplicationContext()));
                } else if (!password.equals(repeatPassword)) {
                    toastMaker("Passwords do not match");
                    binding.passwordDeleteAccountEditText.setSelection(0);
                    binding.repeatPasswordDeleteAccountEditText.setSelection(0);
                } else {
                    toastMaker("Invalid passwords");
                    binding.passwordDeleteAccountEditText.setSelection(0);
                    binding.repeatPasswordDeleteAccountEditText.setSelection(0);
                }
            } else {
                toastMaker("Account has been successfully deleted");
            }
        });
    }

    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.preference_userId_key), loggedInUserId);
        sharedPrefEditor.apply();
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

    static Intent settingsActivityIntentFactory(Context context) {
        return new Intent(context, SettingsActivity.class);
    }
}