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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.daclink.project2.database.DiveHubRepository;
import com.daclink.project2.database.entities.User;
import com.daclink.project2.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;
    private User user;
    private DiveHubRepository repository;

    int loggedInUserId;
    private static final String MAIN_ACTIVITY_USER_ID = "com.daclink.project2.MAIN_ACTIVITY_USER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
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
                // unfinished
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


    private void changeUsername() {
        showChangeUsernameDialog();
    }

    private void showChangeUsernameDialog() {
        final Dialog dialog = new Dialog(SettingsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.change_username_dialog);

        EditText changeUserEditText = dialog.findViewById(R.id.changeUsernameDialogEditText);
        Button submitButton = dialog.findViewById(R.id.changeUsernameDialogButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitNewUserName(changeUserEditText);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void submitNewUserName(EditText changeUserEditText) {
        User currentUser = this.user;
        String newUsername = changeUserEditText.getText().toString();
        LiveData<User> userObserver = repository.getUserByUserName(newUsername);
        userObserver.observe(this, user -> {
            if (user != null) {
                if (currentUser.getUsername().equals(newUsername)) {
                    toastMaker(String.format("Username is already %s", newUsername));
                } else {
                    toastMaker(String.format("%s is already taken.", newUsername));
                }
            } else {
                LiveData<User> userObserver2 = repository.getUserByUserId(loggedInUserId);
                userObserver2.observe(this, newUser -> {
                    newUser.setUsername(newUsername);
                    repository.insertUser(newUser);
                    this.user = newUser;
                    toastMaker(String.format("Username was successfully changed to %s", newUsername));
                    userObserver2.removeObservers(this);
                });
                userObserver.removeObservers(this);
            }
        });
    }

    private void changePassword() {
        showChangePasswordDialog();
    }

    private void showChangePasswordDialog() {
        final Dialog dialog = new Dialog(SettingsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.change_password_dialog);

        EditText changePasswordEditText = dialog.findViewById(R.id.changePasswordDialogEditText);
        EditText changeRepeatPasswordEditText = dialog.findViewById(R.id.changeRepeatPasswordDialogEditText);
        Button submitButton = dialog.findViewById(R.id.changePasswordDialogButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitNewPassword(changePasswordEditText, changeRepeatPasswordEditText);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void submitNewPassword(EditText changePasswordEditText, EditText changeRepeatPasswordEditText) {
        LiveData<User> userObserver = repository.getUserByUserId(loggedInUserId);

        userObserver.observe(this, user -> {
            String password = changePasswordEditText.getText().toString();
            String repeatPassword = changeRepeatPasswordEditText.getText().toString();
            if (password.equals(repeatPassword)) {
                user.setPassword(password);
                repository.insertUser(user);
                toastMaker("Password successfully changed!");
                userObserver.removeObservers(this);
            } else {
                toastMaker("Passwords don't match");
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
                    if (user.isAdmin()) {
                        toastMaker("You can't delete an admin user");
                        binding.passwordDeleteAccountEditText.setSelection(0);
                        binding.repeatPasswordDeleteAccountEditText.setSelection(0);
                        userObserver.removeObservers(this);
                        return;
                    }
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
                userObserver.removeObservers(this);
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