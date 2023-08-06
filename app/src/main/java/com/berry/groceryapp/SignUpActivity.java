package com.berry.groceryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
        getSupportActionBar().hide();

        binding.buttonLogin.setOnClickListener(view1 -> {
            CommonUtils.hideKeyboard(this);
            onBackPressed();
        });

        binding.buttonSignUp.setOnClickListener(view1 -> {

            CommonUtils.hideKeyboard(this);

            if (isUserCredentialsValid(view1)) {

                dataBaseHelper = new DataBaseHelper(SignUpActivity.this);
                //try{
                if (dataBaseHelper.insertUser(userName, email, password)) {
                    CommonUtils.showMaterialDialog(this, getString(R.string.registration_is_successful), (dialogInterface, i) -> {
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


    protected boolean isUserCredentialsValid(View view) {
        userName = binding.editTextUsername.getText().toString();
        email = binding.editTextEmail.getText().toString();
        password = binding.editTextPassword.getText().toString();
        confirm = binding.editTextCPassword.getText().toString();

        //userName,password,confirmPassword cannot be empty
        if (userName.length() == 0 || email.length() == 0 || password.length() == 0 || confirm.length() == 0) {
            CommonUtils.showCustomSnackBar(view, getString(R.string.please_fill_all_the_fields));
            return false;
        } else if (!CommonUtils.isEmailValid(email)) {
            //validating email
            CommonUtils.showCustomSnackBar(view, getString(R.string.please_enter_valid_email));
            return false;
        } else if (!password.equals(confirm)) {
            //passwords should match
            CommonUtils.showCustomSnackBar(view, getString(R.string.passwords_should_match));
            return false;
        }

        return true;
    }

}