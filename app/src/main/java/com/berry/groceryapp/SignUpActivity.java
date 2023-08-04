package com.berry.groceryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.berry.groceryapp.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {

    String userName, email, password, confirm;
    DataBaseHelper dataBaseHelper;
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.buttonLogin.setOnClickListener(view1 -> {
            onBackPressed();
        });

        binding.buttonSignUp.setOnClickListener(view1 -> {

            if (isUserCredentialsValid()) {

                dataBaseHelper = new DataBaseHelper(SignUpActivity.this);
                //try{
                if (dataBaseHelper.insertUser(userName, email, password)) {
                    CommonUtils.showMaterialDialog(this, "Registration is Successful!", (dialogInterface, i) -> {
                        Intent registerIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(registerIntent);
                        finish();
                    });
                }
                //catch (InsertException e) {
                else {
//                    setResult(Activity.RESULT_CANCELED,returnIntent);
                    //    e.printStackTrace();
                }


            }


        });
    }


    protected boolean isUserCredentialsValid() {
        userName = binding.editTextUsername.getText().toString();
        email = binding.editTextEmail.getText().toString();
        password = binding.editTextPassword.getText().toString();
        confirm = binding.editTextCPassword.getText().toString();
        //userName,password,confirmPassword cannot be empty
        if (userName.length() == 0 || email.length() == 0 || password.length() == 0 || confirm.length() == 0) {
            Toast.makeText(SignUpActivity.this, "Fill all the blanks!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        //passwords should match
        if (!password.equals(confirm)) {
            Toast.makeText(SignUpActivity.this, "Passwords should match!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}