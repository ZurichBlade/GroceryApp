package com.berry.groceryapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CommonUtils {

    public static String PREFERENCES = "MySharedPref";
    public static String PREF_USERNAME = "username";


    public static void setStringPref(String key, String value, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString(key, value);
        myEdit.apply();
    }

    public static String getStringPref(String key, Context context) {
        Context appContext = context.getApplicationContext();
        if (appContext instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) appContext;
            SharedPreferences sharedPreferences = activity.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
            return sharedPreferences.getString(key, "");
        } else {
            // If the context is not an instance of AppCompatActivity,
            // try to get the preferences using the application context directly
            SharedPreferences sharedPreferences = appContext.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
            return sharedPreferences.getString(key, "");
        }
    }


    public static void showCustomSnackBar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show();
    }


    public static void openDatePicker(TextView textView, FragmentManager supportFragmentManager) {


        Calendar currentDate = Calendar.getInstance();

        // Set up the MaterialDatePicker with CalendarConstraints
        CalendarConstraints.Builder calendarConstraintsBuilder = new CalendarConstraints.Builder();
        calendarConstraintsBuilder.setValidator(new CalendarConstraints.DateValidator() {
            @Override
            public boolean isValid(long date) {
                // Disable future dates by comparing the selected date with the current date
                return date <= currentDate.getTimeInMillis();
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(@NonNull Parcel parcel, int i) {

            }
        });


        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setCalendarConstraints(calendarConstraintsBuilder.build());
        MaterialDatePicker<Long> datePicker = builder.build();


        datePicker.addOnPositiveButtonClickListener(selectedDateInMillis -> {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(selectedDateInMillis);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String formattedDate = sdf.format(calendar.getTime());
            textView.setText(formattedDate);
        });

        datePicker.show(supportFragmentManager, "DATE_PICKER_TAG");
    }

    public static boolean isEmailValid(String email) {
        String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailPattern);
    }


    public static void hideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Hide the soft keyboard
     *
     * @param context The context from which the keyboard should be hidden
     * @param view    The view that currently has focus
     */
    public static void hideKeyboard(Context context, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showMaterialDialog(Context context, String message, DialogInterface.OnClickListener onPositiveButtonClick) {
        new MaterialAlertDialogBuilder(context)
                .setTitle("Grocery App")
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        onPositiveButtonClick.onClick(dialog, which);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the negative button click (e.g., dismiss the dialog)
                        dialog.dismiss();
                    }
                })
                .show();
    }


}
