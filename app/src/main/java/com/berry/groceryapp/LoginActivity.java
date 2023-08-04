package com.berry.groceryapp;

import static com.berry.groceryapp.CommonUtils.PREFERENCES;
import static com.berry.groceryapp.CommonUtils.PREF_USERNAME;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.berry.groceryapp.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    final static int REGISTER_REQUEST_CODE = 1;
    DataBaseHelper dataBaseHelper;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        dataBaseHelper = new DataBaseHelper(LoginActivity.this);

        // Check if the user is already logged in
        if (isLoggedIn()) {

            String userName = CommonUtils.getStringPref(PREF_USERNAME, LoginActivity.this);

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("USERNAME", userName);
            startActivity(intent);
            finish();
        }


        binding.buttonLogin.setOnClickListener(view1 -> {
            if (isLoginCredentialsValid()) {
                String userName = binding.editTextUsername.getText().toString();
                String password = binding.editTextPassword.getText().toString();
                if (dataBaseHelper.isUserValid(userName, password)) {
                    saveLoginStatus(true);
                    CommonUtils.setStringPref(PREF_USERNAME, userName, LoginActivity.this);
                    Intent homeIntent = new Intent(LoginActivity.this, MainActivity.class);
                    homeIntent.putExtra("USERNAME", userName);
                    startActivity(homeIntent);
                    finish();
                } else {
                    CommonUtils.showMaterialDialog(this, "Username or Password incorrect!", (dialogInterface, i) -> {
                    });
                }
            }
        });

        binding.buttonSignUp.setOnClickListener(view1 -> {
            Intent registerIntent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivityForResult(registerIntent, REGISTER_REQUEST_CODE);
        });


    }


/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTER_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //Toast.makeText(LoginActivity.this,data.getStringExtra("username"), Toast.LENGTH_SHORT).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(LoginActivity.this, "Couldn't register!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LoginActivity.this, "Unexpected result Code!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Unexpected request Code!", Toast.LENGTH_SHORT).show();
        }
    }*/


    protected boolean isLoginCredentialsValid() {
        String userName = binding.editTextUsername.getText().toString();
        String password = binding.editTextPassword.getText().toString();
        if (userName.length() == 0 || password.length() == 0) {
            Toast.makeText(LoginActivity.this, "You should fill the blanks!"
                    , Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void saveLoginStatus(boolean isLoggedIn) {
        SharedPreferences preferences = this.getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();
    }

    private boolean isLoggedIn() {
        SharedPreferences preferences = this.getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        return preferences.getBoolean("isLoggedIn", false);
    }

}