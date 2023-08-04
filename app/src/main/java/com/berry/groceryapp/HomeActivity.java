package com.berry.groceryapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    TextView tvWelcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
        String userName = getIntent().getStringExtra("USERNAME");

//        tvWelcomeMessage = findViewById(R.id.tv_welcome_message);
        tvWelcomeMessage.setText("Welcome " + userName);
    }
}